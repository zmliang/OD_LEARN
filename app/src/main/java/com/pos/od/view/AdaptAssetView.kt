package com.pos.od.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView


class AdaptAssetView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val assetValue: TextView
    private val assetUnit:TextView

    private var value:String = "7890"
    private var unit:String = "CNY"

    private val defaultFontSize = 36f

    init {
        //setBackgroundColor(Color.BLUE)
        orientation = HORIZONTAL

        assetValue = TextView(context)
        assetValue.textSize = defaultFontSize
        assetValue.setTextColor(Color.RED)
        assetValue.text = value
        assetValue.gravity = Gravity.BOTTOM
        addView(assetValue,LayoutParams(WRAP_CONTENT,MATCH_PARENT))
        assetUnit = TextView(context)
        assetUnit.apply {
            setTextColor(Color.BLACK)
            gravity = Gravity.BOTTOM
        }
        assetUnit.text = unit
        val lp = LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        lp.leftMargin = 15
        addView(assetUnit,lp)


        post{
            adaptContent()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    private fun adaptContent(){
        assetValue.textSize = defaultFontSize
        var valueW = assetValue.paint.measureText(value)
        val unitW = assetUnit.paint.measureText(unit)
        var fontSize = assetValue.textSize


        Log.e("zml","value width = "+valueW)
        Log.e("zml","unit width = "+unitW)
        Log.e("zml","width = "+measuredWidth)

        while ((valueW+unitW) >measuredWidth){
            fontSize-=0.5f
            assetValue.textSize = fontSize
            valueW = assetValue.paint.measureText(value)
        }


        requestLayout()
        Log.e("zml","=====================")
        Log.e("zml","value width = "+valueW)
        Log.e("zml","unit width = "+unitW)
        Log.e("zml","width = "+measuredWidth)
    }

    fun setValue(txt:String){
        this.value = txt
        adaptContent()
        assetValue.text = value
    }

}