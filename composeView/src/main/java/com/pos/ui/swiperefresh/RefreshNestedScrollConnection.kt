package com.pos.ui.swiperefresh

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RefreshNestedScrollConnection (
    val state:SwipeRefreshState,
    private val coroutineScope: CoroutineScope,
    private val onRefresh: () -> Unit,
    private val onLoadMore: () -> Unit,

):NestedScrollConnection{

    var dragMultiplier = 0.5f
    var refreshEnable = false
    var loadMoreEnable = false
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return when{
            //刷新和加载更多都没启用
            (!refreshEnable && !loadMoreEnable)->Offset.Zero

            (state.isRefreshing || state.isLoading || state.isFinishing) -> available
            //header和footer都没有显示
            (state.indicatorOffset == 0f) ->Offset.Zero
            //正在拖动时
            source == NestedScrollSource.Drag -> onScroll(available)
            else -> Offset.Zero
        }
    }



    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return when{
            (!refreshEnable&&!loadMoreEnable) -> Offset.Zero
            // 当正在刷新或正在加载或处理正在完成时，则不进行消费，直接拦截
            state.isRefreshing || state.isLoading || state.isFinishing -> Offset.Zero
            // 当前如果正在拖动并且节点所消耗的量consumed为0时，则进行滚动处理
            source == NestedScrollSource.Drag && consumed.y == 0f -> onScroll(available)
            else -> Offset.Zero
        }
    }


    override suspend fun onPreFling(available: Velocity): Velocity {
        if (!(state.isRefreshing || state.isLoading || state.isFinishing)) {
            when {
                refreshEnable && state.isExceededRefreshTrigger() -> onRefresh()
                loadMoreEnable && state.isExceededLoadMoreTrigger() -> onLoadMore()
                state.indicatorOffset != 0f && !state.isSwipeInProgress -> state.animateOffsetTo(0f)
            }
        }

        state.isSwipeInProgress = false
        return super.onPreFling(available)
    }


    private fun onScroll(available:Offset):Offset{
        if (available.y != 0f) {
            if (available.y < 0f && !loadMoreEnable) {
                return Offset.Zero
            }
            if (available.y > 0f && !refreshEnable) {
                return Offset.Zero
            }
            state.isSwipeInProgress = true
            val dragConsumed = available.y * dragMultiplier
            coroutineScope.launch {
                state.dispatchOffsetDelta(dragConsumed)
            }
            return available
        }
        return Offset.Zero
    }

}



















