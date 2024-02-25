package com.zml.guide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi


internal class GuideLayer : RelativeLayout ,IGuiderLayer{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint:Paint = Paint()
    private val bgColor = 0x9900ff00
    private val mode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private val screenSize:Point

    private var highlightCornerRadius = 12f

    private val bitmap :Bitmap
    private val bmpCanvas:Canvas

    private var curStep = 0

    private val targetAreas = arrayListOf<RectF>()
    private var curTargetRect:RectF = RectF()
    private var stepView:View?=null

    private var onNextStepListener:OnNextStepListener?=null

    override fun setOnNextStepListener(listener:OnNextStepListener):IGuiderLayer{
        this.onNextStepListener = listener
        return this
    }
    init {
        paint.isAntiAlias = true
        paint.color = bgColor.toInt()
        paint.setXfermode(mode)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        screenSize = getScreenSize(context)
        bitmap = Bitmap.createBitmap(screenSize.x, screenSize.y, Bitmap.Config.ARGB_8888)

        bmpCanvas = Canvas(bitmap)

        setWillNotDraw(false)

        clipChildren = false
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bmpCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR)
        bmpCanvas.drawColor(bgColor.toInt())
        bmpCanvas.drawRoundRect(curTargetRect,highlightCornerRadius,highlightCornerRadius, paint)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                //nextStep()
            }
        }
        return true
    }
    companion object{
        fun attach(context: Context): IGuiderLayer {
            return GuideLayer(context)
        }
    }
    private fun getDisplay(context: Context): Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getDisplayApiR(context)
        } else {
            getDisplayApiL(context)
        }
    }
    private fun getDisplayApiL(context: Context): Display? {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    private fun getDisplayApiR(context: Context): Display? {
        return context.display
    }
    private fun getScreenSize(context: Context): Point {
        val display = getDisplay(context)
        val size = Point()
        display?.getRealSize(size)
        return size
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bitmap.recycle()
        targetAreas.clear()
    }

    override fun addTarget(view: View):IGuiderLayer{
        view.post {
            val loc = IntArray(2)
            view.getLocationOnScreen(loc)
            val w = view.measuredWidth
            val h = view.measuredHeight
            val x = loc[0].toFloat()
            val y = loc[1].toFloat()
            targetAreas.add(RectF(x,y,x+w,y+h))
        }
        return this
    }

     override fun nextStep(){
        if (curStep>=targetAreas.size){
            dismiss()
            return
        }
        this.removeView(stepView)
        val step = curStep
        curTargetRect = targetAreas[curStep++]
        this.onNextStepListener.let {
            it?.onNext(step,this)
        }
        this.addView(stepView)
        invalidate()

    }

    override fun context(): Context {
        return context
    }

    override fun show(){
        if (this.context is Activity){
            val decor = (this.context as Activity).window.decorView
            if (decor is FrameLayout){
                decor.addView(this, LayoutParams(MATCH_PARENT, MATCH_PARENT))
                this.post {
                    nextStep()
                }
            }
        }else{

        }

    }


    override fun dismiss(){
        if (parent == null){
            return
        }
        (parent as ViewGroup).removeView(this)
    }

    override fun getStepView(): View? {
        return stepView
    }

    override fun setStepView(view: View): GuideLayer {
        this.stepView = view
        return this
    }


    override fun currentTargetRect(): RectF {
        return curTargetRect
    }


}