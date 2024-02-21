package com.pos.od.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import kotlin.math.abs
import kotlin.math.pow


/**

public boolean dispatchTouchEvent(MotionEvent ev)
1）return true:表示消耗了当前事件，有可能是当前 View 的onTouchEvent或者是子 View 的dispatchTouchEvent消费了，事件终止，不再传递。
2）return false:调用父 ViewGroup 或 Activity 的onTouchEvent。 （不再往下传）。
3）return super.dispatcherTouchEvent:则继续往下(子 View )传递，或者是调用当前 View 的 onTouchEvent 方法;
总结： 用来分发事件，即事件序列的大门，如果事件传递到当前 View 的onTouchEvent或者是子 View 的dispatchTouchEvent，即该方法被调用了。
另外如果不消耗 ACTION_DOWN 事件，那么 down, move, up 事件都与该 View 无关，交由父类处理(父类的onTouchEvent方法)


public boolean onInterceptTouchEvent(MotionEvent ev)
1）return true:ViewGroup 将该事件拦截，交给自己的onTouchEvent处理。
2）return false:继续传递给子元素的dispatchTouchEvent处理。
3）return super.dispatcherTouchEvent:事件默认不会被拦截。
总结： 在dispatchTouchEvent内部调用，顾名思义就是判断是否拦截某个事件。(注：ViewGroup 才有的方法，View 因为没有子View了，所以不需要也没有该方法) 。
而且这一个事件序列（当前和其它事件）都只能由该 ViewGroup 处理，并且不会再调用该onInterceptTouchEvent方法去询问是否拦截。


public boolean onTouchEvent(MotionEvent ev)
1）return true:事件消费，当前事件终止。
2）return false:交给父 View 的onTouchEvent。
3）return super.dispatcherTouchEvent:默认处理事件的逻辑和返回 false 时相同。


 */
class PullToRefreshLayout @JvmOverloads constructor(context: Context,attr:AttributeSet? = null, defStyleAttr:Int = 0)
    :ViewGroup(context,attr,defStyleAttr) {
    private val INDICATOR = "headerIndicator"
    private val INDICATOR_HEIGHT = dp2px(50)

    private var headerIndicator :TextView = TextView(this.context)

    private var offsetTop = 0

    private var indicatorOffsetTop = -INDICATOR_HEIGHT

    private var intercepted = false

    private var scrolledView: View?=null

    private var downY = 0f

    private val touchSlot = ViewConfiguration.get(context).scaledTouchSlop


    init {
        headerIndicator.tag = INDICATOR
        headerIndicator.setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray))
        headerIndicator.text = "头部的indicator"
        headerIndicator.gravity = Gravity.CENTER
        headerIndicator.layoutParams= LayoutParams(LayoutParams.MATCH_PARENT,INDICATOR_HEIGHT*2)
        headerIndicator.offsetTopAndBottom(-INDICATOR_HEIGHT)

        addView(headerIndicator,0)
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (v in children){
            if (v.tag!=INDICATOR && scrolledView == null){
                scrolledView = v
            }
            measureChild(v,widthMeasureSpec,heightMeasureSpec)
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec))
    }
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //Log.e("zml","onLayout top = $top")
        for (v in children){
            if (v.tag == INDICATOR){
                v.layout(left,top+indicatorOffsetTop.times(2),right,INDICATOR_HEIGHT+indicatorOffsetTop)
            }else{
                v.layout(left,top+offsetTop,right,bottom+offsetTop)
            }
        }
    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (scrolledView?.canScrollVertically(-1) == true){
            return false
        }
        when(ev?.action){
            MotionEvent.ACTION_DOWN-> {
                intercepted=false
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE-> {
                if (ev.y-downY>touchSlot){
                    intercepted = true
                }
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL->{
                intercepted=false
            }
        }
        return intercepted
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_MOVE->{
                val diff = (event.y-downY).times(0.5f)
                val originalDragPercent: Float = diff / INDICATOR_HEIGHT
                if (originalDragPercent < 0) {
                    return false
                }
                val mDragPercent = 1f.coerceAtMost(abs(originalDragPercent))
                val extraOS: Float = abs(diff) - INDICATOR_HEIGHT

                val tensionSlingshotPercent =
                    0f.coerceAtLeast(extraOS.coerceAtMost(INDICATOR_HEIGHT * 2.0f) / INDICATOR_HEIGHT)
                val tensionPercent = (tensionSlingshotPercent / 4 - (tensionSlingshotPercent / 4).toDouble().pow(2.0)).toFloat() * 2f
                val extraMove = INDICATOR_HEIGHT * tensionPercent * 2
                val targetY = (INDICATOR_HEIGHT * mDragPercent + extraMove)

                childVerticalOffset(targetY-offsetTop)
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL->{
                animateResetPos()
            }
        }
        return true
    }

    private fun animateResetPos() {
        val animator:ValueAnimator = ValueAnimator.ofFloat(offsetTop.toFloat(),0f)
        animator.duration = 300
        animator.addUpdateListener {
            val offset: Float = (it.animatedValue as Float) - offsetTop
            childVerticalOffset(offset, false)
        }
        animator.start()
    }


    private fun childVerticalOffset(offset:Float,invalidate:Boolean = true){
        Log.e("zml","child offset = $offset,offsetTop=$offsetTop,  headerIndicator.top = ${INDICATOR_HEIGHT}")
        scrolledView?.offsetTopAndBottom(offset.toInt())
        offsetTop = scrolledView?.top!!
        //headerIndicator.scrollY = offset.toInt()
        headerIndicator.offsetTopAndBottom(offset.toInt())

        if (invalidate){
            invalidate()
        }

    }

    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }




}