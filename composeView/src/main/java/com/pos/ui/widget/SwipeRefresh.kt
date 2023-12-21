package com.pos.ui.widget

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pos.ui.pages.allTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign

val tokens = listOf(
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),

    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),

    Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
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
//            .pointerInput(Unit){
//                detectTransformGestures(
//                    onGesture = { centroid, pan, gestureZoom, gestureRotate ->
//                        Log.i("zml","detectTransformGestures: centroid=$centroid   " +
//                                ",  pan=$pan   ,  gestureZoom=$gestureZoom   ," +
//                                "  gestureRotate=$gestureRotate")
//                        val oldValue = offset.floatValue
//                        offset.floatValue = (offset.floatValue + delta).coerceIn(scrollStateRange)
//                        offset.floatValue - oldValue
//                    })
//            }
            .background(Color.LightGray)
            //.overscroll(overscroll)
        ,
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            offset.floatValue.roundToInt().toString(),
            style = TextStyle(fontSize = 32.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(offset.floatValue.dp)
               // .verticalScroll(rememberScrollState())
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

