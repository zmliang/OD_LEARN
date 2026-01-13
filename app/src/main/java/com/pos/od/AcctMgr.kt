package com.pos.od

// MainActivity.kt
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.*
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        surfaceView = findViewById(R.id.surfaceView)
        faceOverlayView = findViewById(R.id.faceOverlayView)
        switchCameraButton = findViewById(R.id.switchCameraButton)
        switchRatioButton = findViewById(R.id.switchRatioButton)

        surfaceView.holder.addCallback(surfaceCallback)

        switchCameraButton.setOnClickListener {
            switchCamera()
        }

        switchRatioButton.setOnClickListener {
            switchAspectRatio()
        }

        if (allPermissionsGranted()) {
            startBackgroundThread()
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

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            openCamera()
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // Update overlay size when surface changes
            adjustOverlaySize()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // Not needed for now
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
            val surface = surfaceView.holder.surface

            // Get optimal preview size based on current aspect ratio
            previewSize = chooseOptimalSize()

            // Adjust SurfaceView and Overlay to match preview aspect ratio to avoid distortion
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
        val outputSizes = map?.getOutputSizes(Surface::class.java) ?: emptyArray() // Use Surface.class for SurfaceView

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

    // Adjust SurfaceView layout params to match preview size aspect ratio
    private fun adjustSurfaceViewSize(size: Size) {
        val layoutParams = surfaceView.layoutParams as ViewGroup.LayoutParams
        val ratio = size.width.toFloat() / size.height.toFloat()

        // Assuming we want to fit width, adjust height (handle portrait/landscape)
        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = (surfaceView.width / ratio).toInt()
        } else {
            layoutParams.width = (surfaceView.height * ratio).toInt()
        }
        surfaceView.layoutParams = layoutParams
    }

    // Adjust FaceOverlayView to match SurfaceView size
    private fun adjustOverlaySize() {
        val layoutParams = faceOverlayView.layoutParams as ViewGroup.LayoutParams
        layoutParams.width = surfaceView.width
        layoutParams.height = surfaceView.height
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
        runOnUiThread {
            faceOverlayView.clear()
        }
    }

    override fun onPause() {
        closeCamera()
        stopBackgroundThread()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (surfaceView.holder.surface.isValid) {
            openCamera()
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}

// Custom View for drawing face rectangles
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