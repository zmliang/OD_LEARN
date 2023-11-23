package com.pos.ui.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.pos.R
import kotlinx.coroutines.delay


@Composable
fun AnimationButton() {
    Log.e("zml", "进入了这个compose方法里了")
    var zoomIn by remember { mutableStateOf(false) }

    val radius by animateDpAsState(
        targetValue = if (zoomIn) 85.dp else 30.dp, label = "",
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 200),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier
        .size(100.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(Color.Green)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            zoomIn = !zoomIn
        },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(radius)
                .clip(RoundedCornerShape(radius / 2))
                .background(Color.White)
        ) {

        }
    }

}


enum class FloatPos(private val value: Int) {
    BOTTOM(1),
    MIDDLE(2),
    TOP(3);

    fun getValue(): Int {
        return value
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun scrollNotice() {

    var pos by remember { mutableStateOf(FloatPos.BOTTOM) }

    var duration by remember { mutableIntStateOf(500) }

    var _delay by remember { mutableIntStateOf(10) }

    var _alpha by remember { mutableFloatStateOf(0.0f) }

    var _transY by remember { mutableStateOf(60) }


    when (pos) {
        FloatPos.BOTTOM -> {
            _transY = 30
            _alpha = 1.0f
            duration = 500
            _delay = 0
        }

        FloatPos.TOP -> {
            _transY = 60
            _alpha = 0.0f
            duration = 200
            _delay = 0
        }

        FloatPos.MIDDLE -> {
            _transY = 0
            _alpha = 0.0f
            duration = 500
            _delay = 1500
        }
    }

    val tY by animateDpAsState(
        targetValue = _transY.dp, label = "",
        animationSpec = TweenSpec(durationMillis = duration, delay = _delay)
    ) {
        when (pos) {//next pos
            FloatPos.MIDDLE -> {
                pos = FloatPos.TOP
            }

            FloatPos.TOP -> {
                pos = FloatPos.BOTTOM
            }

            FloatPos.BOTTOM -> {
                pos = FloatPos.MIDDLE
            }
        }
    }
    val a by animateFloatAsState(
        targetValue = _alpha, label = "",
        animationSpec = TweenSpec(durationMillis = duration, delay = _delay)
    )

    LaunchedEffect(key1 = "float_pos") {
        delay(300)
        pos = FloatPos.MIDDLE
    }

    Box(
        modifier = Modifier
            .padding(vertical = tY.value.dp)
            .alpha(a)
            .wrapContentWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0x88888888)),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(horizontal = 15.dp)) {
            Image(painter = painterResource(id = R.drawable.aave),
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentDescription = "avatar")
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontSize =  15.sp)){
                    append("用户")
                }
                withStyle(style = SpanStyle(color = Color("#CD7F32".toColorInt()), fontSize =  15.sp, fontWeight = FontWeight.Bold)){
                    append("187***6971")
                }
                withStyle(style = SpanStyle(color = Color.Black, fontSize =  15.sp)){
                    append("刚刚充值了1W元")
                }
            })
        }

    }


}