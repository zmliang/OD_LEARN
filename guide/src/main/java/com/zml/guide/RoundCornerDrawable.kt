package com.zml.guide

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable


class RoundCornerDrawable(private var cornerRadius: Int = 0,
                          bgColor: Int = Color.WHITE,
                          private val triangleSize: Float = 20f,
                          private val trianglePos: Position = Position.LEFT,
                          private val triangleOffset:Offset = Offset(0f,0f)
) : Drawable() {

    private var paint: Paint = Paint()
    private var rect:RectF = RectF()
    private val trianglePath: Path = Path()


    init {
        paint.color = bgColor
        paint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        rect.set(bounds)
        canvas.drawRoundRect(rect, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)

        if (triangleSize > 0f){
            trianglePath.reset()

            val w = rect.right-rect.left
            val h = rect.bottom-rect.top

            when(trianglePos){
                Position.TOP->{
                    trianglePath.moveTo(0f+triangleOffset.x, 0f+triangleOffset.y)
                    trianglePath.lineTo(triangleSize+triangleOffset.x, -(triangleSize+triangleOffset.y))
                    trianglePath.lineTo(triangleSize.times(2)+triangleOffset.x, 0f+triangleOffset.y)
                    trianglePath.close()
                }
                Position.BOTTOM->{
                    trianglePath.moveTo(0f, h)
                    trianglePath.lineTo(triangleSize, h+triangleSize)
                    trianglePath.lineTo(triangleSize.times(2), h)
                    trianglePath.close()
                }
                Position.LEFT->{
                    trianglePath.moveTo(0f, 0f)
                    trianglePath.lineTo(-triangleSize, triangleSize)
                    trianglePath.lineTo(0f,triangleSize.times(2))
                    trianglePath.close()
                }
                Position.RIGHT->{
                    trianglePath.moveTo(w, 0f)
                    trianglePath.lineTo(w+triangleSize, triangleSize)
                    trianglePath.lineTo(w,triangleSize.times(2))
                    trianglePath.close()
                }
            }
            canvas.drawPath(trianglePath, paint)


        }

    }


    private fun drawTriangle(canvas:Canvas,path: Path){
        trianglePath.moveTo(0f, 0f)
        trianglePath.lineTo(triangleSize, -triangleSize)
        trianglePath.lineTo(triangleSize.times(2), 0f)
        trianglePath.close()
        canvas.drawPath(trianglePath, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.setColorFilter(colorFilter)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

}