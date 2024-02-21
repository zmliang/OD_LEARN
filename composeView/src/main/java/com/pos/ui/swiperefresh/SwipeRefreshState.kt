package com.pos.ui.swiperefresh


import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun rememberSwipeRefreshState(
    isRefreshing: Boolean,
    isLoading: Boolean
): SwipeRefreshState {
    return remember { SwipeRefreshState(isRefreshing, isLoading) }.apply {
        this.isRefreshing = isRefreshing
        this.isLoading = isLoading
    }
}
class SwipeRefreshState(isRefreshing: Boolean, isLoading: Boolean) {
    private val mutatorMutex = MutatorMutex()

    var isRefreshing by mutableStateOf(isRefreshing)

    var isLoading by mutableStateOf(isLoading)

    var isFinishing by mutableStateOf(false)

    /**
     * 当前是否正在滑动（仅在单次滑动操作中，仅会记录[_indicatorOffset]的值不为0时的状态，即：需要触发至少显示一次Header或Footer时，才会被判定为正在滑动）
     */
    var isSwipeInProgress: Boolean by mutableStateOf(false)
        internal set


    var refreshTrigger: Float by mutableFloatStateOf(Float.MAX_VALUE)
        internal set

    /**
     * 触发加载更多的最小距离
     */
    var loadMoreTrigger: Float by mutableFloatStateOf(Float.MIN_VALUE)
        internal set

    /**
     * Header可滑动的最大偏移量
     */
    var headerMaxOffset by mutableFloatStateOf(0f)
        internal set

    /**
     * Footer可滑动的最小偏移量
     */
    var footerMinOffset by mutableFloatStateOf(0f)
        internal set


    /**
     * 是否达到触发刷新的条件
     *
     */
    internal fun isExceededRefreshTrigger() = indicatorOffset >= refreshTrigger

    /**
     * 是否达到触发加载更多的条件
     */
    internal fun isExceededLoadMoreTrigger() = indicatorOffset <= loadMoreTrigger


    private val _indicatorOffset = Animatable(0f)

    val indicatorOffset get() = _indicatorOffset.value


    /**
     * Header的状态
     */
    var headerState by mutableStateOf(HeaderState.PullDownToRefresh)
        internal set

    /**
     * Footer的状态
     */
    var footerState by mutableStateOf(FooterState.PullUpToLoad)
        internal set


    internal suspend fun animateOffsetTo(offset:Float){
        mutatorMutex.mutate {
            _indicatorOffset.animateTo(offset){
                if (indicatorOffset == 0f && isFinishing){
                    isFinishing = false
                    updateFooterState()
                    updateHeaderState()
                }
            }
            if (!isFinishing){
                updateFooterState()
                updateHeaderState()
            }
        }
    }


    private fun updateHeaderState(){
        headerState = when{
            isRefreshing->HeaderState.Refreshing
            isSwipeInProgress->HeaderState.ReleaseToRefresh
            else->HeaderState.PullDownToRefresh
        }
    }

    private fun updateFooterState(){
        footerState = when{
            isRefreshing->FooterState.Loading
            isSwipeInProgress->FooterState.ReleaseToLoad
            else->FooterState.PullUpToLoad
        }
    }


    internal suspend fun dispatchOffsetDelta(delta:Float){
        mutatorMutex.mutate(MutatePriority.UserInput) {
            val offset = if (indicatorOffset > 0f) {
                indicatorOffset.plus(delta).coerceIn(0f, headerMaxOffset)
            } else if (indicatorOffset < 0f) {
                indicatorOffset.plus(delta).coerceIn(footerMinOffset, 0f)
            } else {
                indicatorOffset.plus(delta)
            }

            _indicatorOffset.snapTo(offset)
            if(!isFinishing) {
                updateHeaderState()
                updateFooterState()
            }
        }
    }
}