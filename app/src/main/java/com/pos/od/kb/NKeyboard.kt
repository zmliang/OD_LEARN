package com.pos.od.kb

import android.R.id.keyboardView
import android.app.Activity
import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Vibrator
import android.text.Editable
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.example.od.R


class NKeyboard private constructor():KeyboardView.OnKeyboardActionListener {
    private var isShowStart = false
    private var isHideStart = false

    private var isShowed = false

    private var toBackSize:Float = 0f
    private val duration:Long = 200

    private var showAnimation:TranslateAnimation?=null
    private var hideAnimation:TranslateAnimation?=null

    private var mScreenHeight=-1

    private var originalScrollPosInScr: IntArray = IntArray(4)
    private var originalScrollPosInPar: IntArray = IntArray(4)


    private var targetEditText:EditText?=null
    private var targetScrollView:View?=null
    private var targetKeyView:View?=null
    init {
        initAnimation()
    }
    companion object {
        private var instance: NKeyboard?=null
            get() {
                if (field == null) {
                    field = NKeyboard()
                }
                return field
            }
        fun get(): NKeyboard{
            return instance!!
        }
    }

    fun isKeyBoardShowing():Boolean{
        return true
    }

    private fun scrollUp(mEditText:EditText?,keyContainer: View?,mScrollLayout: View?){
        val keyboardHeight: Int = keyContainer?.height?:0 // 获取键盘布局的高度

        val keyStartY = mScreenHeight - keyboardHeight
        val position = IntArray(2)
        mEditText?.getLocationOnScreen(position)
        val mEditTextBottomY: Int = position[1] + (mEditText?.height?:0)
        if (mEditTextBottomY > keyStartY) {
            // 说明这个 EditText 的底部在 键盘 View 顶部以下, 即 EditText 被键盘遮挡了
            val to = (keyStartY - mEditTextBottomY - 10).toFloat() // 为负值, 需要往上移动的距离, 往上为负值, 往下为正值
            if (position[1] + to < originalScrollPosInScr[1]) {
                // 说明, scrollLayout 被往上顶起之后, EditText 所在位置可能会被 scrollLayout 上面的其他 View 遮挡或者重合了导致显示不准确,
                // 那么顶起操作在这里就显示不合适了, 所以这里最好是添加一个长文本显示功能
                // 说明往上顶起之后 mEditText 会被遮挡, 即 mEditText 的 top 距离顶部的距离 小于 要移动的距离
                // 这里就不需要顶起了, 需要显示一个长文本显示页面
                // TODO... 添加一个长文本显示功能, 不过这里的长文本显示似乎没有什么意义
                return
            }
            toBackSize = to
            mScrollLayout?.animate()?.translationYBy(toBackSize)
                ?.setDuration(duration)?.start()
        }
    }

    private fun scrollDown(isHide:Boolean,editText: EditText?,keyContainer:View?,mScrollLayout: View?):Boolean{
        if (editText == null || keyContainer == null || mScrollLayout == null){
            return false
        }
        var thisScrollY = 0
        if (!isHide && editText != null) {
            // 这种情况说明是点击了一个 EditText, 则需要判断是否需要移动 mScrollLayout 来适应 SafeKeyboard 的显示
            val mEditPos = IntArray(2)
            editText.getLocationOnScreen(mEditPos)
            Log.e("zml", "0: " + mEditPos[0] + ", 1: " + mEditPos[1])
            val keyboardHeight: Int = keyContainer.height
            val keyStartY: Int = mScreenHeight - keyboardHeight
            getOriginalScrollLayoutPos(mScrollLayout = mScrollLayout)
            if (editText.height + 10 > keyStartY - originalScrollPosInScr[1]) {
                // editText 的高度 大于 SafeKeyboard 上边界到 mScrollLayout 上边界的距离, 即 editText 无法完全显示
                // TODO... 添加一个长文本输入功能
                return false
            } else {
                // 可以正常显示
                if (mEditPos[1] < originalScrollPosInScr[1]) {
                    // 说明当前的 editText 的 top 位置已经被其他布局遮挡, 需要布局往下滑动一点, 使 editText 可以完全显示
                    thisScrollY = originalScrollPosInScr[1] - mEditPos[1] + 10 // 正值
                } else if (mEditPos[1] + editText.height > keyStartY) {
                    // 说明当前的 editText 的 bottom 位置已经被其他布局遮挡, 需要布局往上滑动一点, 使 editText 可以完全显示
                    thisScrollY = keyStartY - mEditPos[1] - editText.height //负值
                } else {
                    // 各项均正常, 不需要重新滑动
                    Log.i("zml", "Need not to scroll")
                    return false
                }
            }
        }

        toBackSize += thisScrollY
        if (isHide) {
            mScrollLayout.animate().setDuration(duration)
                .translationYBy(-toBackSize).start()
            toBackSize = 0f
        } else {
            mScrollLayout.animate().setDuration(duration)
                .translationYBy(thisScrollY.toFloat()).start()
        }

        return true
    }


    fun clear(activity: Activity){
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        var keyboardLayout = rootView.getTag(R.id.keyboard_top_bar) as View?
        rootView.removeView(keyboardLayout)
    }

    private fun initScreenHeight(context: Context){
        if (mScreenHeight !=-1){
            return
        }
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        // 给 metrics 赋值
        val metrics = DisplayMetrics()
        wm?.defaultDisplay?.getMetrics(metrics)
        mScreenHeight = metrics.heightPixels
    }

    private fun getOriginalScrollLayoutPos(mScrollLayout:View) {
        if (originalScrollPosInScr[0] == 0 && originalScrollPosInScr[1] == 0) {
            val pos = intArrayOf(0, 0)
            mScrollLayout.getLocationOnScreen(pos)
            originalScrollPosInScr[0] = pos[0]
            originalScrollPosInScr[1] = pos[1]
            originalScrollPosInScr[2] = pos[0] + mScrollLayout.width
            originalScrollPosInScr[3] = pos[1] + mScrollLayout.height
        }
        if (originalScrollPosInPar[0] == 0 && originalScrollPosInPar[1] == 0 && originalScrollPosInPar[2] == 0 && originalScrollPosInPar[3] == 0
        ) {
            originalScrollPosInPar[0] = mScrollLayout.left
            originalScrollPosInPar[1] = mScrollLayout.top
            originalScrollPosInPar[2] = mScrollLayout.right
            originalScrollPosInPar[3] = mScrollLayout.bottom
        }
    }
    fun show(editText: EditText,scrollView: View,activity: Activity,animation:Boolean=true){
        if (isShowStart || isShowed){
            return
        }
        this.targetEditText = editText
        this.targetScrollView = scrollView
        initScreenHeight(activity)
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        var keyboardLayout = rootView.getTag(R.id.keyboard_top_bar) as View?
        if (keyboardLayout == null){
            keyboardLayout = LayoutInflater.from(activity).inflate(R.layout.custom_keyboard,null)
            rootView.setTag(R.id.keyboard_top_bar,keyboardLayout)
            val lp = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            keyboardLayout.layoutParams = lp
            rootView.addView(keyboardLayout)
            val kbv = keyboardLayout.findViewById<KeyboardView>(R.id.real_kbv)
            keyboardLayout.findViewById<TextView>(R.id.finished).setOnClickListener {
                hide(activity)
            }
            kbv.isEnabled = true
            kbv.isPreviewEnabled = false
            kbv.setOnKeyboardActionListener(this)
        }
        this.targetKeyView = keyboardLayout
        keyboardLayout?.let {
            if (animation){
                it.clearAnimation()
                it.startAnimation(showAnimation)
            }else{
                it.visibility = VISIBLE
            }
        }
    }

    fun hide(activity: Activity,animation: Boolean=true){
        if (isHideStart || !isShowed){
            return
        }
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        var keyboardLayout = rootView.getTag(R.id.keyboard_top_bar) as View?
        keyboardLayout?.clearAnimation()
        if (animation){
            keyboardLayout?.startAnimation(hideAnimation)
        }else{
            keyboardLayout?.visibility = GONE
        }
    }
    private fun initAnimation(){
        showAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f
        )
        hideAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            1.0f
        )
        showAnimation?.duration = duration
        hideAnimation?.duration = duration

        showAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                targetKeyView?.visibility = VISIBLE
                isShowStart = true
            }
            override fun onAnimationEnd(animation: Animation?) {
                isShowed=true
                isShowStart = false
                scrollUp(targetEditText,targetKeyView,targetScrollView)
                targetKeyView?.clearAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        hideAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                isHideStart = true
                // 动画持续时间 HIDE_TIME 结束后, 不管什么操作, 都需要执行, 把 isHideStart 值设为 false; 否则
                // 如果 onAnimationEnd 因为某些原因没有执行, 会影响下一次使用
                //safeHandler.removeCallbacks(hideEnd)
                //safeHandler.postDelayed(hideEnd, keyboardConfig.hideDuration)
            }

            override fun onAnimationEnd(animation: Animation?) {
                isShowed=false
                isHideStart = false
                targetKeyView?.visibility = GONE
                scrollDown(true,targetEditText,targetKeyView,targetScrollView)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
    override fun onPress(primaryCode: Int) {
        
    }

    override fun onRelease(primaryCode: Int) {
        
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        this.targetEditText?.let {
            try {
                val editable: Editable = it.text
                val start: Int = it.selectionStart
                val end: Int = it.selectionEnd
                if (primaryCode === Keyboard.KEYCODE_CANCEL) {
                    // 隐藏键盘

                } else if (primaryCode === Keyboard.KEYCODE_DELETE || primaryCode === -35) {

                    // 回退键,删除字符
                    if (editable.isNotEmpty()) {
                        if (start == end) { //光标开始和结束位置相同, 即没有选中内容
                            editable.delete(start - 1, start)
                        } else { //光标开始和结束位置不同, 即选中EditText中的内容
                            editable.delete(start, end)
                        }
                    } else {

                    }
                }  else {
                    // 输入键盘值
                    editable.replace(start, end, primaryCode.toChar().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onText(text: CharSequence?) {
        
    }

    override fun swipeLeft() {
        
    }

    override fun swipeRight() {
        
    }

    override fun swipeDown() {
        
    }

    override fun swipeUp() {
        
    }

}