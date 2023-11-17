package com.pos.ui.pages

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.pos.R
import com.pos.ui.widget.InfiniteCarousel
import com.pos.ui.widget.PullRefresh
import com.pos.ui.widget.Resident
import com.pos.ui.widget.Token
import com.pos.ui.widget.TokenList
import com.pos.ui.widget.overScrollVertical
import com.pos.ui.widget.parabolaScrollEasing
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home() {
    val tokens = listOf(
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    )



    PullRefresh(
        onRefresh = {
            delay(2000)
        },
        loadingIndicator = {
            Box(modifier = Modifier.padding(10.dp)) {
                CircularProgressIndicator(Modifier.size(200.dp))
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {

            item {
                InfiniteCarousel()
            }
            item {
                Resident()
            }

            stickyHeader {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .background(Color.Green)
                ) {
                    Text(text = "这是吸顶的title")
                }
            }

            allTokens(tokens)

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
class CusOverScroll @ExperimentalFoundationApi
constructor(override val effectModifier: Modifier, override val isInProgress: Boolean) :
    OverscrollEffect {
    @ExperimentalFoundationApi
    override suspend fun applyToFling(
        velocity: Velocity,
        performFling: suspend (Velocity) -> Velocity
    ) {
        TODO("Not yet implemented")
    }

    @ExperimentalFoundationApi
    override fun applyToScroll(
        delta: Offset,
        source: NestedScrollSource,
        performScroll: (Offset) -> Offset
    ): Offset {
        TODO("Not yet implemented")
    }

}


@Composable
fun _test() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .scrollable(
                state = rememberScrollableState(consumeScrollDelta = object : (Float) -> Float {
                    override fun invoke(p1: Float): Float {
                        return 100f
                    }
                }),
                orientation = Orientation.Vertical,
                enabled = true,

                )
    ) {
        // Your scrollable content
        Surface(
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Sample Content",
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

fun LazyListScope.allTokens(tokens: List<Token>) {

    items(tokens) {
        tokenItem(token = it)
        Spacer(modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .height(0.2.dp)
            .background(Color("#ff141416".toColorInt())))
    }
}


@Preview()
@Composable
fun tokenItem(token: Token) {
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(20.dp))

        Image(
            painter = painterResource(R.drawable.bgt),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(50.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))


        Text(text = "BTC/", fontSize = 28.sp)
        Text(text = "USDT", fontSize = 22.sp)

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
            ){
            Text(
                text = "1712", fontSize = 28.sp
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "+12%", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Green)
            Spacer(modifier = Modifier.width(20.dp))
        }
    }

}