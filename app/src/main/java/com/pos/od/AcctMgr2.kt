package com.pos.od

// MainActivity.kt
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.*
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView
    private lateinit var faceOverlayView: FaceOverlayView
    private lateinit var switchCameraButton: Button
    private lateinit var switchRatioButton: Button

    private var cameraDevice: CameraDevice? = null
    private var cameraCaptureSession: CameraCaptureSession? = null
    private var backgroundHandler: Handler? = null
    private var backgroundThread: HandlerThread? = null

    private var currentCameraId: String = CameraCharacteristics.LENS_FACING_BACK.toString() // Default to back camera
    private var currentRatioIndex: Int = 0
    private val aspectRatios = listOf(4f/3f, 16f/9f, 1f/1f) // Example ratios: 4:3, 16:9, 1:1

    private var previewSize: Size? = null
    private var sensorOrientation: Int = 0
    private var isFrontCamera: Boolean = false

    private var oesTextureId: Int = -1
    private var surfaceTexture: SurfaceTexture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        glSurfaceView = findViewById(R.id.glSurfaceView)
        faceOverlayView = findViewById(R.id.faceOverlayView)
        switchCameraButton = findViewById(R.id.switchCameraButton)
        switchRatioButton = findViewById(R.id.switchRatioButton)

        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(CameraRenderer())
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        switchCameraButton.setOnClickListener {
            switchCamera()
        }

        switchRatioButton.setOnClickListener {
            switchAspectRatio()
        }

        if (allPermissionsGranted()) {
            startBackgroundThread()
            openCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startBackgroundThread()
                openCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground").also { it.start() }
        backgroundHandler = Handler(backgroundThread!!.looper)
    }

    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun openCamera() {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            // Check if face detection is supported
            val characteristics = manager.getCameraCharacteristics(currentCameraId)
            val faceDetectModes = characteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES) ?: intArrayOf()
            if (faceDetectModes.isEmpty()) {
                Toast.makeText(this, "设备不支持人脸检测", Toast.LENGTH_SHORT).show()
            }
            sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
            isFrontCamera = characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT

            manager.openCamera(currentCameraId, stateCallback, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
            cameraDevice = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            onDisconnected(camera)
        }
    }

    private fun createCameraPreviewSession() {
        try {
            // Wait for OES texture to be ready
            if (oesTextureId == -1) return

            surfaceTexture = SurfaceTexture(oesTextureId)
            surfaceTexture?.setDefaultBufferSize(previewSize!!.width, previewSize!!.height)
            val surface = Surface(surfaceTexture)

            // Get optimal preview size based on current aspect ratio
            previewSize = chooseOptimalSize()

            // Adjust GLSurfaceView and Overlay to match preview aspect ratio
            adjustSurfaceViewSize(previewSize!!)
            adjustOverlaySize()

            val builder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            builder.addTarget(surface)

            // Enable face detection
            builder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, CaptureRequest.STATISTICS_FACE_DETECT_MODE_SIMPLE)

            cameraDevice?.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    if (cameraDevice == null) return

                    cameraCaptureSession = session
                    try {
                        session.setRepeatingRequest(builder.build(), captureCallback, backgroundHandler)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Toast.makeText(this@MainActivity, "Failed to configure camera", Toast.LENGTH_SHORT).show()
                }
            }, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Capture callback to get face detection results
    private val captureCallback = object : CameraCaptureSession.CaptureCallback() {
        override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
            super.onCaptureCompleted(session, request, result)
            processFaceDetection(result)
        }
    }

    // Process detected faces and draw rectangles
    private fun processFaceDetection(result: CaptureResult) {
        val faces = result.get(CaptureResult.STATISTICS_FACES) ?: return
        val faceRects = mutableListOf<Rect>()
        for (face in faces) {
            val bounds = face.bounds
            // Transform coordinates to match preview
            val transformedRect = transformFaceCoordinates(bounds)
            faceRects.add(transformedRect)
        }
        // Update overlay view with face rectangles
        runOnUiThread {
            faceOverlayView.setFaces(faceRects)
        }
    }

    // Transform face coordinates from sensor to preview coordinates
    private fun transformFaceCoordinates(bounds: Rect): Rect {
        val matrix = Matrix()
        val previewWidth = previewSize!!.width
        val previewHeight = previewSize!!.height

        // Handle rotation and mirroring for front camera
        val viewWidth = faceOverlayView.width
        val viewHeight = faceOverlayView.height

        // Compensate for sensor orientation
        matrix.postRotate(sensorOrientation.toFloat(), previewWidth / 2f, previewHeight / 2f)

        if (isFrontCamera) {
            matrix.postScale(-1f, 1f, previewWidth / 2f, previewHeight / 2f) // Mirror for front camera
        }

        // Scale to view size
        val scaleX = viewWidth.toFloat() / previewWidth
        val scaleY = viewHeight.toFloat() / previewHeight
        matrix.postScale(scaleX, scaleY)

        val floatBounds = RectF(bounds)
        matrix.mapRect(floatBounds)
        return Rect(floatBounds.left.toInt(), floatBounds.top.toInt(), floatBounds.right.toInt(), floatBounds.bottom.toInt())
    }

    // Choose optimal preview size matching the desired aspect ratio
    private fun chooseOptimalSize(): Size {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val characteristics = manager.getCameraCharacteristics(currentCameraId)
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val outputSizes = map?.getOutputSizes(SurfaceTexture::class.java) ?: emptyArray()

        val desiredRatio = aspectRatios[currentRatioIndex]

        var optimalSize = outputSizes[0]
        var minDiff = Float.MAX_VALUE

        val displaySize = resources.displayMetrics.let { Size(it.widthPixels, it.heightPixels) }

        for (size in outputSizes) {
            val ratio = size.width.toFloat() / size.height.toFloat()
            val diff = abs(ratio - desiredRatio)
            if (diff < minDiff && size.width <= displaySize.width && size.height <= displaySize.height) {
                minDiff = diff
                optimalSize = size
            } else if (diff == minDiff && size.width > optimalSize.width) {
                optimalSize = size
            }
        }
        return optimalSize
    }

    // Adjust GLSurfaceView layout params to match preview size aspect ratio
    private fun adjustSurfaceViewSize(size: Size) {
        val layoutParams = glSurfaceView.layoutParams as ViewGroup.LayoutParams
        val ratio = size.width.toFloat() / size.height.toFloat()

        // Assuming we want to fit width, adjust height (handle portrait/landscape)
        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = (glSurfaceView.width / ratio).toInt()
        } else {
            layoutParams.width = (glSurfaceView.height * ratio).toInt()
        }
        glSurfaceView.layoutParams = layoutParams
    }

    // Adjust FaceOverlayView to match GLSurfaceView size
    private fun adjustOverlaySize() {
        val layoutParams = faceOverlayView.layoutParams as ViewGroup.LayoutParams
        layoutParams.width = glSurfaceView.width
        layoutParams.height = glSurfaceView.height
        faceOverlayView.layoutParams = layoutParams
    }

    private fun switchCamera() {
        closeCamera()
        currentCameraId = if (currentCameraId == CameraCharacteristics.LENS_FACING_BACK.toString()) {
            CameraCharacteristics.LENS_FACING_FRONT.toString()
        } else {
            CameraCharacteristics.LENS_FACING_BACK.toString()
        }
        openCamera()
    }

    private fun switchAspectRatio() {
        closeCamera()
        currentRatioIndex = (currentRatioIndex + 1) % aspectRatios.size
        openCamera()
    }

    private fun closeCamera() {
        cameraCaptureSession?.close()
        cameraCaptureSession = null
        cameraDevice?.close()
        cameraDevice = null
        surfaceTexture?.release()
        surfaceTexture = null
        runOnUiThread {
            faceOverlayView.clear()
        }
    }

    override fun onPause() {
        closeCamera()
        stopBackgroundThread()
        glSurfaceView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
        startBackgroundThread()
        openCamera()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    // OpenGL Renderer for camera preview with filter
    inner class CameraRenderer : GLSurfaceView.Renderer {

        private var program: Int = 0
        private var positionHandle: Int = 0
        private var texCoordHandle: Int = 0
        private var mvpMatrixHandle: Int = 0

        private val vertexBuffer: FloatBuffer
        private val texCoordBuffer: FloatBuffer

        private val vertexShaderCode = """
            attribute vec4 vPosition;
            attribute vec2 aTexCoord;
            varying vec2 vTexCoord;
            uniform mat4 uMVPMatrix;
            void main() {
                gl_Position = uMVPMatrix * vPosition;
                vTexCoord = aTexCoord;
            }
        """.trimIndent()

        private val fragmentShaderCode = """
            #extension GL_OES_EGL_image_external : require
            precision mediump float;
            varying vec2 vTexCoord;
            uniform samplerExternalOES sTexture;
            void main() {
                vec4 color = texture2D(sTexture, vTexCoord);
                // Simple grayscale filter
                float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
                gl_FragColor = vec4(gray, gray, gray, color.a);
            }
        """.trimIndent()

        private val vertices = floatArrayOf(
            -1f, -1f, 0f,
            1f, -1f, 0f,
            -1f, 1f, 0f,
            1f, 1f, 0f
        )

        private val texCoords = floatArrayOf(
            0f, 0f,
            1f, 0f,
            0f, 1f,
            1f, 1f
        )

        init {
            vertexBuffer = ByteBuffer.allocateDirect(vertices.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().apply {
                put(vertices)
                position(0)
            }
            texCoordBuffer = ByteBuffer.allocateDirect(texCoords.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().apply {
                put(texCoords)
                position(0)
            }
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(0f, 0f, 0f, 1f)

            // Create OES texture
            val textures = IntArray(1)
            GLES20.glGenTextures(1, textures, 0)
            oesTextureId = textures[0]
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, oesTextureId)
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat())
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)

            // Compile shaders
            val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
            val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
            program = GLES20.glCreateProgram()
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, fragmentShader)
            GLES20.glLinkProgram(program)

            // Trigger camera session creation now that texture is ready
            runOnUiThread { createCameraPreviewSession() }
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
        }

        override fun onDrawFrame(gl: GL10?) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

            surfaceTexture?.updateTexImage()

            GLES20.glUseProgram(program)

            positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
            texCoordHandle = GLES20.glGetAttribLocation(program, "aTexCoord")
            mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")

            GLES20.glEnableVertexAttribArray(positionHandle)
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer)

            GLES20.glEnableVertexAttribArray(texCoordHandle)
            GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 8, texCoordBuffer)

            // Set MVP matrix (identity for full screen quad)
            val mvpMatrix = FloatArray(16)
            Matrix.setIdentityM(mvpMatrix, 0)
            // Adjust for camera orientation if needed (e.g., rotate 90 degrees for portrait)
            if (sensorOrientation == 90 || sensorOrientation == 270) {
                Matrix.rotateM(mvpMatrix, 0, sensorOrientation.toFloat(), 0f, 0f, 1f)
            }
            if (isFrontCamera) {
                Matrix.scaleM(mvpMatrix, 0, -1f, 1f, 1f) // Mirror front camera
            }
            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, oesTextureId)

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

            GLES20.glDisableVertexAttribArray(positionHandle)
            GLES20.glDisableVertexAttribArray(texCoordHandle)
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)
        }

        private fun loadShader(type: Int, shaderCode: String): Int {
            val shader = GLES20.glCreateShader(type)
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
            return shader
        }
    }
}

// Custom View for drawing face rectangles (unchanged)
class FaceOverlayView(context: Context) : android.view.View(context) {
    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private var faces: List<Rect> = emptyList()

    fun setFaces(newFaces: List<Rect>) {
        faces = newFaces
        invalidate()
    }

    fun clear() {
        faces = emptyList()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (face in faces) {
            canvas.drawRect(face, paint)
        }
    }
}