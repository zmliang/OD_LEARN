package com.pos.ui.widget

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pos.ui.pages.allTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sign



private const val DragMultiplier = 0.5f

val tokens = listOf(
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),

    )
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeRefresh(){

    val offset = remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    // Create the overscroll controller
    val overscroll = remember(scope) { OffsetOverscrollEffect(scope) }
    // let's build a scrollable that scroll until -512 to 512
    val scrollStateRange = (-80f).rangeTo(80f)
    Box(
        Modifier
            .size(500.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    val oldValue = offset.floatValue
                    offset.floatValue = (offset.floatValue + delta).coerceIn(scrollStateRange)
                    offset.floatValue - oldValue
                },
                overscrollEffect = overscroll
            )
            .background(Color.LightGray)

        ,
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            offset.floatValue.roundToInt().toString(),
            style = TextStyle(fontSize = 32.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(offset.floatValue.dp)
                .overscroll(overscroll)
        )

        LazyColumn(modifier =
        Modifier
            .offset(x = 0.dp, y = offset.floatValue.dp)
            .overscroll(overscroll)
        )
        {
            allTokens(tokens)
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
class OffsetOverscrollEffect(private val scope: CoroutineScope) : OverscrollEffect {
    private val overscrollOffset = Animatable(0f)
    override fun applyToScroll(
        delta: Offset,
        source: NestedScrollSource,
        performScroll: (Offset) -> Offset
    ): Offset {
        //检查滚动方向和越界偏移方向是否相同
        val sameDirection = sign(delta.y).toDouble().toFloat() == sign(overscrollOffset.value)
        val consumedByPreScroll = if (abs(overscrollOffset.value) > 0.5 && !sameDirection) {
            //方向不同，并且越界偏移量大于0.5
            val prevOverscrollValue = overscrollOffset.value
            val newOverscrollValue = overscrollOffset.value + delta.y
            if (sign(prevOverscrollValue) != sign(newOverscrollValue)) {
                // sign changed, coerce to start scrolling and exit
                scope.launch { overscrollOffset.snapTo(0f) }
                Offset(x = 0f, y = delta.y + prevOverscrollValue)
            } else {
                scope.launch {
                    overscrollOffset.snapTo(overscrollOffset.value + delta.y)
                }
                delta.copy(x = 0f)
            }
        } else {
            Offset.Zero
        }
        val leftForScroll = delta - consumedByPreScroll
        val consumedByScroll = performScroll(leftForScroll)
        val overscrollDelta = leftForScroll - consumedByScroll

        if (abs(overscrollDelta.y) > 0.5 && source == NestedScrollSource.Drag) {
            scope.launch {

                overscrollOffset.snapTo(overscrollOffset.value + overscrollDelta.y * 0.3f)
            }
        }
        return consumedByPreScroll + consumedByScroll
    }
    override suspend fun applyToFling(
        velocity: Velocity,
        performFling: suspend (Velocity) -> Velocity
    ) {
        val consumed = performFling(velocity)
        // when the fling happens - we just gradually animate our overscroll to 0
        val remaining = velocity - consumed
        overscrollOffset.animateTo(
            targetValue = 0f,
            initialVelocity = remaining.y,
            animationSpec = spring()
        )
    }
    override val isInProgress: Boolean
        get() = overscrollOffset.value != 0f
    // as we're building an offset modifiers, let's offset of our value we calculated
    override val effectModifier: Modifier = Modifier.offset {
        IntOffset(x = 0, y = overscrollOffset.value.roundToInt())
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedScrollConnectionSample() {

    val toolbarHeight = 48.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }

    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
            items(100) { index ->
                Text("I'm item $index", modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            }
        }

        TopAppBar(
            modifier = Modifier
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
        )
    }
}


@Composable
fun NestedScrollDispatcherSample() {
    // Let's take Modifier.draggable (which doesn't have nested scroll build in, unlike Modifier
    // .scrollable) and add nested scroll support our component that contains draggable

    // this will be a generic components that will work inside other nested scroll components.
    // put it inside LazyColumn or / Modifier.verticalScroll to see how they will interact

    // first, state and it's bounds
    val basicState = remember { mutableStateOf(0f) }
    val minBound = -100f
    val maxBound = 100f
    // lambda to update state and return amount consumed
    val onNewDelta: (Float) -> Float = { delta ->
        val oldState = basicState.value
        val newState = (basicState.value + delta).coerceIn(minBound, maxBound)
        basicState.value = newState
        newState - oldState
    }
    // create a dispatcher to dispatch nested scroll events (participate like a nested scroll child)
    val nestedScrollDispatcher = remember { NestedScrollDispatcher() }

    // create nested scroll connection to react to nested scroll events (participate like a parent)
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // we have no fling, so we're interested in the regular post scroll cycle
                // let's try to consume what's left if we need and return the amount consumed
                val vertical = available.y
                val weConsumed = onNewDelta(vertical)
                return Offset(x = 0f, y = weConsumed)
            }
        }
    }
    Box(
        Modifier
            .size(100.dp)
            .background(Color.LightGray)
            // attach ourselves to nested scroll system
            .nestedScroll(connection = nestedScrollConnection, dispatcher = nestedScrollDispatcher)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    // here's regular drag. Let's be good citizens and ask parents first if they
                    // want to pre consume (it's a nested scroll contract)
                    val parentsConsumed = nestedScrollDispatcher.dispatchPreScroll(
                        available = Offset(x = 0f, y = delta),
                        source = NestedScrollSource.Drag
                    )
                    // adjust what's available to us since might have consumed smth
                    val adjustedAvailable = delta - parentsConsumed.y
                    // we consume
                    val weConsumed = onNewDelta(adjustedAvailable)
                    // dispatch as a post scroll what's left after pre-scroll and our consumption
                    val totalConsumed = Offset(x = 0f, y = weConsumed) + parentsConsumed
                    val left = adjustedAvailable - weConsumed
                    nestedScrollDispatcher.dispatchPostScroll(
                        consumed = totalConsumed,
                        available = Offset(x = 0f, y = left),
                        source = NestedScrollSource.Drag
                    )
                    // we won't dispatch pre/post fling events as we have no flinging here, but the
                    // idea is very similar:
                    // 1. dispatch pre fling, asking parents to pre consume
                    // 2. fling (while dispatching scroll events like above for any fling tick)
                    // 3. dispatch post fling, allowing parent to react to velocity left
                }
            )
    ) {
        Text(
            "State: ${basicState.value.roundToInt()}",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}



//=================
//=================
//=================


@Composable
fun rememberSwipeRefreshState(
    isRefreshing: Boolean
): SwipeRefreshState {
    return remember {
        SwipeRefreshState(
            isRefreshing = isRefreshing
        )
    }.apply {
        this.isRefreshing = isRefreshing
    }
}

@Composable
fun SwipeRefresh_zml(
    state: SwipeRefreshState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    swipeEnabled: Boolean = true,
    refreshTriggerDistance: Dp = 80.dp,
    indicatorAlignment: Alignment = Alignment.TopCenter,
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    indicator: @Composable (state: SwipeRefreshState, refreshTrigger: Dp) -> Unit = { s, trigger ->
       Log.e("zml","s = $s, trigger=$trigger")

        //SwipeRefreshIndicator(s, trigger)
    },
    clipIndicatorToPadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh = rememberUpdatedState(onRefresh)

    // Our LaunchedEffect, which animates the indicator to its resting position
    LaunchedEffect(state.isSwipeInProgress) {
        if (!state.isSwipeInProgress) {
            // If there's not a swipe in progress, rest the indicator at 0f
            state.animateOffsetTo(0f)
        }
    }

    val refreshTriggerPx = with(LocalDensity.current) { refreshTriggerDistance.toPx() }

    // Our nested scroll connection, which updates our state.
    val nestedScrollConnection = remember(state, coroutineScope) {
        SwipeRefreshNestedScrollConnection(state, coroutineScope) {

            updatedOnRefresh.value.invoke()
        }
    }.apply {
        this.enabled = swipeEnabled
        this.refreshTrigger = refreshTriggerPx
    }

    Box(modifier
        .nestedScroll(connection = nestedScrollConnection)

    ) {
        content()

        Box(
            Modifier
                .let { if (!clipIndicatorToPadding) it.clipToBounds() else it }
                .padding(indicatorPadding)
                .matchParentSize()
                .let { if (clipIndicatorToPadding) it.clipToBounds() else it }
                .offset(y = -refreshTriggerDistance + state.indicatorOffset.dp)
        ) {
            Box(Modifier
                .align(indicatorAlignment)
            ) {
                indicator(state, refreshTriggerDistance)
            }
        }
    }
}



private class SwipeRefreshNestedScrollConnection(
    private val state: SwipeRefreshState,
    private val coroutineScope: CoroutineScope,
    private val onRefresh: () -> Unit,
) : NestedScrollConnection {
    var enabled: Boolean = false
    var refreshTrigger: Float = 0f

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset{

        Log.e("zml","available=$available")

        val ret = when {
            // If swiping isn't enabled, return zero
            !enabled -> Offset.Zero
            // If we're refreshing, return zero
            state.isRefreshing -> Offset.Zero
            // If the user is swiping up, handle it
            source == NestedScrollSource.Drag && available.y < 0 -> onScroll(available)
            else -> Offset.Zero
        }
        return ret
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        // If swiping isn't enabled, return zero
        !enabled -> Offset.Zero
        // If we're refreshing, return zero
        state.isRefreshing -> Offset.Zero
        // If the user is swiping down and there's y remaining, handle it
        source == NestedScrollSource.Drag && available.y > 0 -> onScroll(available)
        else -> Offset.Zero
    }

    private fun onScroll(available: Offset): Offset {
        if (available.y > 0) {
            state.isSwipeInProgress = true
        } else if (state.indicatorOffset.roundToInt() == 0) {
            state.isSwipeInProgress = false
        }

        val newOffset = (available.y * DragMultiplier + state.indicatorOffset).coerceAtLeast(0f)
        val dragConsumed = newOffset - state.indicatorOffset
        Log.e("zml","dragConsumed=$dragConsumed")
        return if (dragConsumed.absoluteValue >= 0.5f) {
            coroutineScope.launch {
                state.dispatchScrollDelta(dragConsumed)
            }
            // Return the consumed Y
            Offset(x = 0f, y = dragConsumed / DragMultiplier)
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        // If we're dragging, not currently refreshing and scrolled
        // past the trigger point, refresh!
        if (!state.isRefreshing && state.indicatorOffset >= refreshTrigger) {
            onRefresh()
        }

        // Reset the drag in progress state
        state.isSwipeInProgress = false

        // Don't consume any velocity, to allow the scrolling layout to fling
        return Velocity.Zero
    }
}



class SwipeRefreshState(
    isRefreshing: Boolean,
) {
    private val _indicatorOffset = Animatable(0f)
    private val mutatorMutex = MutatorMutex()

    /**
     * Whether this [SwipeRefreshState] is currently refreshing or not.
     */
    var isRefreshing: Boolean by mutableStateOf(isRefreshing)

    /**
     * Whether a swipe/drag is currently in progress.
     */
    var isSwipeInProgress: Boolean by mutableStateOf(false)
        internal set

    /**
     * The current offset for the indicator, in pixels.
     */
    val indicatorOffset: Float get() = _indicatorOffset.value

    internal suspend fun animateOffsetTo(offset: Float) {
        mutatorMutex.mutate {
            _indicatorOffset.animateTo(offset)
        }
    }

    /**
     * Dispatch scroll delta in pixels from touch events.
     */
    internal suspend fun dispatchScrollDelta(delta: Float) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            _indicatorOffset.snapTo(_indicatorOffset.value + delta)
        }
    }



}
