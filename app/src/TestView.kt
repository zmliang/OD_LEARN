package com.yjkj.chainup.wedegit

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.yjkj.chainup.R
import kotlin.math.roundToInt

class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val clRoot: ConstraintLayout
    private val tvTitle: TextView
    private val llImgs: LinearLayout
    private val clRootLp: LayoutParams

    private var isExpanded = false
    private var runningAnimator: AnimatorSet? = null

    private val noBounceInterpolator = DecelerateInterpolator()

    private val translateOffsetPx = 46f
    private val paddingHCollapsedPx = dp(4f).roundToInt()
    private val paddingHExpandedPx = dp(8f).roundToInt()
    private val paddingBottomCollapsedPx = dp(3f).roundToInt()
    private val paddingBottomExpandedPx = dp(8f).roundToInt()
    private val paddingTopPx = dp(3f).roundToInt()

    init {
        clipChildren = true
        LayoutInflater.from(context).inflate(R.layout.test_layout, this, true)
        clRoot = findViewById(R.id.cl_root)
        tvTitle = findViewById(R.id.tv_title)
        llImgs = findViewById(R.id.ll_imgs)
        clRootLp = clRoot.layoutParams as LayoutParams

        clRoot.clipChildren = true
        clRoot.clipToPadding = true

        tvTitle.setOnClickListener { toggle() }
        post { applyCollapsedState(animate = false) }
    }

    override fun onDetachedFromWindow() {
        runningAnimator?.removeAllListeners()
        runningAnimator?.cancel()
        runningAnimator = null
        setAnimationClipping(enabled = true)
        super.onDetachedFromWindow()
    }

    /** 取消动画并从父容器移除；配合 ViewStub 短暂展示后调用。 */
    fun release() {
        runningAnimator?.removeAllListeners()
        runningAnimator?.cancel()
        runningAnimator = null
        setAnimationClipping(enabled = true)
        (parent as? ViewGroup)?.removeView(this)
    }

    fun toggle() {
        if (isExpanded) collapse() else expand()
    }

    fun expand() {
        if (isExpanded) return
        runningAnimator?.cancel()
        isExpanded = true
        playExpandCollapseAnimation(expand = true)
    }

    fun collapse() {
        if (!isExpanded) return
        runningAnimator?.cancel()
        isExpanded = false
        playExpandCollapseAnimation(expand = false)
    }

    private fun applyCollapsedState(animate: Boolean) {
        if (animate) {
            collapse()
            return
        }
        isExpanded = false
        llImgs.visibility = View.GONE
        llImgs.translationX = 0f
        llImgs.translationY = 0f
        llImgs.scaleX = 1f
        llImgs.scaleY = 1f
        llImgs.alpha = 1f
        applyRootInsets(expanded = false)
        resetRootSize()
    }

    private fun playExpandCollapseAnimation(expand: Boolean) {
        val collapsedSize = measureRootSize(expanded = false)
        val expandedSize = measureRootSize(expanded = true)

        val startW: Int
        val startH: Int
        val endW: Int
        val endH: Int
        if (expand) {
            startW = collapsedSize.first
            startH = collapsedSize.second
            endW = expandedSize.first
            endH = expandedSize.second
            prepareExpandStartState(startW, startH)
        } else {
            startW = expandedSize.first
            startH = expandedSize.second
            endW = collapsedSize.first
            endH = collapsedSize.second
            prepareCollapseStartState(startW, startH)
            setAnimationClipping(enabled = false)
            llImgs.bringToFront()
        }

        val scaleFrom = if (expand) 0.3f else 1f
        val scaleTo = if (expand) 1f else 0.3f
        val alphaFrom = if (expand) 0f else 1f
        val alphaTo = if (expand) 1f else 0f

        val transDuration = if (expand) {
            TRANSLATION_SCALE_DURATION_MS
        } else {
            COLLAPSE_TRANSLATION_DURATION_MS
        }
        val transYAnim = createImgsPositionAnimator(expand, transDuration)
        val scaleDuration = if (expand) {
            TRANSLATION_SCALE_DURATION_MS
        } else {
            COLLAPSE_SCALE_DURATION_MS
        }
        val scaleXAnim = ObjectAnimator.ofFloat(llImgs, View.SCALE_X, scaleFrom, scaleTo).apply {
            duration = scaleDuration
            interpolator = noBounceInterpolator
        }
        val scaleYAnim = ObjectAnimator.ofFloat(llImgs, View.SCALE_Y, scaleFrom, scaleTo).apply {
            duration = scaleDuration
            interpolator = noBounceInterpolator
        }
        val alphaDuration = if (expand) ALPHA_DURATION_MS else COLLAPSE_ALPHA_DURATION_MS
        val alphaAnim = ObjectAnimator.ofFloat(llImgs, View.ALPHA, alphaFrom, alphaTo).apply {
            duration = alphaDuration
            startDelay = if (expand) ALPHA_START_DELAY_MS else 0L
            interpolator = if (expand) EXPAND_ALPHA_INTERPOLATOR else COLLAPSE_ALPHA_INTERPOLATOR
        }

        val paddingStartFrom = if (expand) paddingHCollapsedPx else paddingHExpandedPx
        val paddingEndFrom = paddingStartFrom
        val paddingBottomFrom = if (expand) paddingBottomCollapsedPx else paddingBottomExpandedPx
        val paddingStartTo = if (expand) paddingHExpandedPx else paddingHCollapsedPx
        val paddingEndTo = paddingStartTo
        val paddingBottomTo = if (expand) paddingBottomExpandedPx else paddingBottomCollapsedPx

        val sizeAnim = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ROOT_SIZE_DURATION_MS
            interpolator = noBounceInterpolator
            addUpdateListener {
                val fraction = it.animatedFraction
                applyAnimatedRootSize(
                    width = lerp(startW, endW, fraction),
                    height = lerp(startH, endH, fraction),
                    paddingStart = lerp(paddingStartFrom, paddingStartTo, fraction),
                    paddingEnd = lerp(paddingEndFrom, paddingEndTo, fraction),
                    paddingBottom = lerp(paddingBottomFrom, paddingBottomTo, fraction),
                )
            }
        }

        runningAnimator = AnimatorSet().apply {
            playTogether(transYAnim, scaleXAnim, scaleYAnim, alphaAnim, sizeAnim)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    setAnimationClipping(enabled = true)
                    if (expand) {
                        finishExpandedState()
                    } else {
                        finishCollapsedState()
                    }
                    runningAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    onAnimationEnd(animation)
                }
            })
            start()
        }
    }

    private fun prepareExpandStartState(startW: Int, startH: Int) {
        applyRootInsets(expanded = false)
        lockRootSize(startW, startH)
        llImgs.visibility = View.VISIBLE
        llImgs.translationX = 0f
        llImgs.translationY = 0f
        llImgs.alpha = 0f
        llImgs.scaleX = 1f
        llImgs.scaleY = 1f
        applyImgsPivotFromTitle()
        llImgs.scaleX = 0.3f
        llImgs.scaleY = 0.3f
    }

    private fun prepareCollapseStartState(startW: Int, startH: Int) {
        applyRootInsets(expanded = true)
        lockRootSize(startW, startH)
        llImgs.visibility = View.VISIBLE
        llImgs.translationX = 0f
        llImgs.translationY = 0f
        llImgs.alpha = 1f
        llImgs.scaleX = 1f
        llImgs.scaleY = 1f
        applyImgsPivotFromTitle()
    }

    private fun finishExpandedState() {
        applyRootInsets(expanded = true)
        resetRootSize()
        llImgs.translationX = 0f
        llImgs.translationY = 0f
        llImgs.scaleX = 1f
        llImgs.scaleY = 1f
        llImgs.alpha = 1f
        llImgs.visibility = View.VISIBLE
    }

    private fun finishCollapsedState() {
        llImgs.visibility = View.GONE
        llImgs.translationX = 0f
        llImgs.translationY = 0f
        llImgs.scaleX = 1f
        llImgs.scaleY = 1f
        llImgs.alpha = 1f
        applyRootInsets(expanded = false)
        resetRootSize()
    }

    private fun createImgsPositionAnimator(expand: Boolean, duration: Long): ObjectAnimator {
        val fromTrans = if (expand) -translateOffsetPx else 0f
        val toTrans = if (expand) 0f else -translateOffsetPx
        llImgs.translationY = fromTrans
        return ObjectAnimator.ofFloat(llImgs, View.TRANSLATION_Y, fromTrans, toTrans).apply {
            this.duration = duration
            interpolator = noBounceInterpolator
        }
    }

    private fun setAnimationClipping(enabled: Boolean) {
        clipChildren = enabled
        clipToPadding = enabled
        clRoot.clipChildren = enabled
        clRoot.clipToPadding = enabled
    }

    private fun applyImgsPivotFromTitle() {
        ensureLayoutForPivot()
        if (llImgs.width == 0 || llImgs.height == 0 || tvTitle.width == 0 || tvTitle.height == 0) {
            return
        }
        val titleCenterX = tvTitle.x + tvTitle.width / 2f
        val titleCenterY = tvTitle.y + tvTitle.height / 2f
        llImgs.pivotX = titleCenterX - llImgs.x
        llImgs.pivotY = titleCenterY - llImgs.y
    }

    private fun ensureLayoutForPivot() {
        clRoot.requestLayout()
        clRoot.layout(clRoot.left, clRoot.top, clRoot.right, clRoot.bottom)
    }

    /**
     * 更新 clRoot 尺寸与 padding，并在有变化时触发布局。
     * 展开/收起都必须 requestLayout，否则宽度/高度动画不会生效。
     */
    private fun applyAnimatedRootSize(
        width: Int,
        height: Int,
        paddingStart: Int,
        paddingEnd: Int,
        paddingBottom: Int,
    ) {
        var changed = false
        if (clRootLp.width != width) {
            clRootLp.width = width
            changed = true
        }
        if (clRootLp.height != height) {
            clRootLp.height = height
            changed = true
        }
        val paddingChanged = clRoot.paddingLeft != paddingStart ||
            clRoot.paddingRight != paddingEnd ||
            clRoot.paddingBottom != paddingBottom
        if (paddingChanged) {
            clRoot.setPadding(paddingStart, paddingTopPx, paddingEnd, paddingBottom)
            changed = true
        } else if (changed) {
            clRoot.requestLayout()
        }
    }

    private fun measureRootSize(expanded: Boolean): Pair<Int, Int> {
        val imgsVisibility = llImgs.visibility
        llImgs.visibility = if (expanded) View.VISIBLE else View.GONE
        applyRootInsets(expanded)
        resetRootSize()
        clRoot.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        )
        val size = clRoot.measuredWidth to clRoot.measuredHeight
        llImgs.visibility = imgsVisibility
        return size
    }

    private fun applyRootInsets(expanded: Boolean) {
        val horizontal = if (expanded) paddingHExpandedPx else paddingHCollapsedPx
        val bottom = if (expanded) paddingBottomExpandedPx else paddingBottomCollapsedPx
        if (clRoot.paddingLeft != horizontal ||
            clRoot.paddingRight != horizontal ||
            clRoot.paddingBottom != bottom
        ) {
            clRoot.setPadding(horizontal, paddingTopPx, horizontal, bottom)
        }
    }

    private fun lockRootSize(width: Int, height: Int) {
        clRootLp.width = width
        clRootLp.height = height
    }

    private fun resetRootSize() {
        clRootLp.width = LayoutParams.WRAP_CONTENT
        clRootLp.height = LayoutParams.WRAP_CONTENT
    }

    private fun lerp(start: Int, end: Int, fraction: Float): Int {
        return (start + (end - start) * fraction).roundToInt()
    }

    private fun dp(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics,
        )
    }

    companion object {
        private val EXPAND_ALPHA_INTERPOLATOR = PathInterpolator(0.33f, 0f, 0.67f, 1f)
        private val COLLAPSE_ALPHA_INTERPOLATOR = PathInterpolator(0.22f, 0.66f, 0.03f, 1f)

        private const val TRANSLATION_SCALE_DURATION_MS = 350L
        private const val COLLAPSE_TRANSLATION_DURATION_MS = 400L
        private const val COLLAPSE_SCALE_DURATION_MS = 400L
        private const val ALPHA_DURATION_MS = 250L
        private const val COLLAPSE_ALPHA_DURATION_MS = 350L
        private const val ALPHA_START_DELAY_MS = 50L
        private const val ROOT_SIZE_DURATION_MS = 400L
    }
}
