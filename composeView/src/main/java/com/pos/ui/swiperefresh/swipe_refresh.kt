package com.pos.ui.swiperefresh

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

@Composable

fun pullToRefresh(
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    headerIndicator: @Composable (SwipeRefreshState) -> Unit ,
    footerIndicator: @Composable (SwipeRefreshState) -> Unit ,
    content:@Composable ()->Unit
){

    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh = rememberUpdatedState(onRefresh)
    val updateOnLoadMore = rememberUpdatedState(onLoadMore)

    val state = rememberSwipeRefreshState(isRefreshing = false, isLoading = false)

    Box(modifier = Modifier.clipToBounds()){
        subCompose(
            refreshEnabled = true,
            loadMoreEnabled = true,
            header = {
                headerIndicator(state)
                     },
            footer = {
                footerIndicator(state)
            }) {headerHeight, footerHeight ->

            val nestedScrollConn = remember(state,coroutineScope) {
                RefreshNestedScrollConnection(
                    state, coroutineScope,
                    onRefresh = {
                        updatedOnRefresh.value.invoke()
                    },
                    onLoadMore = {
                        updateOnLoadMore.value.invoke()
                    }
                )
            }.apply {
                this.refreshEnable = true
                this.loadMoreEnable = true
                this.dragMultiplier = 0.5f
            }

            LaunchedEffect(headerHeight, footerHeight) {
                state.refreshTrigger = headerHeight.times(1.0f)
                state.loadMoreTrigger = -footerHeight.times(1.0f)
            }

            LaunchedEffect(headerHeight, footerHeight) {
                state.headerMaxOffset = headerHeight.times(2.0f)
                state.footerMinOffset = -footerHeight.times(2.0f)
            }
            LaunchedEffect(state.isSwipeInProgress, state.isRefreshing, state.isLoading) {//这几个状态发生变化的时候就会触发effect执行
                if (!state.isSwipeInProgress) {
                    when {
                        state.isRefreshing -> state.animateOffsetTo(headerHeight.toFloat())
                        state.isLoading -> state.animateOffsetTo(-footerHeight.toFloat())
                        state.headerState == HeaderState.Refreshing || state.footerState == FooterState.Loading -> {
                            state.isFinishing = true
                            delay(2000)
                            state.animateOffsetTo(0f)
                        }
                        else -> state.animateOffsetTo(0f)
                    }
                }
            }


            Box(modifier = Modifier.nestedScroll(nestedScrollConn)){
                Box(modifier = Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer {
                        translationY = state.indicatorOffset - headerHeight
                    }
                    .zIndex(1f)
                ) {
                    headerIndicator(state)
                }

                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .graphicsLayer {
                        translationY =  state.indicatorOffset + footerHeight
                    }
                    .zIndex(1f)
                ){
                    footerIndicator(state)
                }

                Box(modifier = Modifier.graphicsLayer {
                    translationY = state.indicatorOffset
                }) {

                    content()
                }

            }

        }
    }


}

@Composable
fun subCompose(
    refreshEnabled: Boolean,
    loadMoreEnabled: Boolean,
    header: @Composable () -> Unit,
    footer: @Composable () -> Unit,
    content: @Composable (headerHeight: Int, footerHeight: Int) -> Unit
){

    SubcomposeLayout{constraints: Constraints ->

        val headerMeasurable = subcompose(slotId = "headerSlot", content = header).firstOrNull()?.measure(constraints)
        val footerMeasurable = subcompose(slotId = "footerSlot", content = footer).firstOrNull()?.measure(constraints)

        val contentMeasurable = subcompose(slotId = "contentSlot",
            content = {
                content(
                    headerMeasurable?.height?.takeIf { refreshEnabled } ?: 0,
                    footerMeasurable?.height?.takeIf { loadMoreEnabled } ?: 0
                )
            }).map { it.measure(constraints) }.first()

        layout(width = contentMeasurable.width, height = contentMeasurable.height) {
            contentMeasurable.placeRelative(0, 0)
        }
    }
}


private fun obtainHeaderOffset(){

}

