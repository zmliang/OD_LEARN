package com.pos.ui.pages


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.overscroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign


@Composable
fun Me(){

    //OverscrollSample()
    OverscrollWithDraggable_After()
    //OverscrollWithDraggable_Before()
}



@OptIn(ExperimentalFoundationApi::class)

@Composable
fun OverscrollSample() {
    @OptIn(ExperimentalFoundationApi::class)

    class OffsetOverscrollEffect(val scope: CoroutineScope) : OverscrollEffect {
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

                    overscrollOffset.snapTo(overscrollOffset.value + overscrollDelta.y * 0.2f)
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
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            offset.floatValue.roundToInt().toString(),
            style = TextStyle(fontSize = 32.sp),
            modifier = Modifier
                .overscroll(overscroll)
        )
    }
}

@Composable
fun OverscrollWithDraggable_Before() {
    var dragPosition by remember { mutableStateOf(0f) }
    val minPosition = -1000f
    val maxPosition = 1000f
    val draggableState = rememberDraggableState { delta ->
        val newPosition = (dragPosition + delta).coerceIn(minPosition, maxPosition)
        dragPosition = newPosition
    }
    Box(
        Modifier
            .size(500.dp)
            .draggable(draggableState, orientation = Orientation.Horizontal),
        contentAlignment = Alignment.Center
    ) {
        Text("Drag position $dragPosition")
    }
}
@OptIn(ExperimentalFoundationApi::class)

@Composable
fun OverscrollWithDraggable_After() {
    var dragPosition by remember { mutableStateOf(0f) }
    val minPosition = -1000f
    val maxPosition = 1000f
    val overscrollEffect = ScrollableDefaults.overscrollEffect()
    val draggableState = rememberDraggableState { delta ->
        // Horizontal, so convert the delta to a horizontal offset
        val deltaAsOffset = Offset(delta, 0f)
        // Wrap the original logic inside applyToScroll
        overscrollEffect.applyToScroll(deltaAsOffset, NestedScrollSource.Drag) { remainingOffset ->
            val remainingDelta = remainingOffset.x
            val newPosition = (dragPosition + remainingDelta).coerceIn(minPosition, maxPosition)
            // Calculate how much delta we have consumed
            val consumed = newPosition - dragPosition
            dragPosition = newPosition
            // Return how much offset we consumed, so that we can show overscroll for what is left
            Offset(consumed, 0f)
        }
    }
    Box(
        Modifier
            // Draw overscroll on the box
            .overscroll(overscrollEffect)

            .size(500.dp)
            .draggable(
                draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    overscrollEffect.applyToFling(Velocity(it, 0f)) { velocity ->
                        if (dragPosition == minPosition || dragPosition == maxPosition) {
                            // If we are at the min / max bound, give overscroll all of the velocity
                            Velocity.Zero
                        } else {
                            // If we aren't at the min / max bound, consume all of the velocity so
                            // overscroll won't show. Normally in this case something like
                            // Modifier.scrollable would use the velocity to update the scroll state
                            // with a fling animation, but just do nothing to keep this simpler.
                            velocity
                        }
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text("Drag position $dragPosition")

    }
}
