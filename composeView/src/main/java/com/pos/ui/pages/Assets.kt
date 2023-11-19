package com.pos.ui.pages

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.pos.ui.widget.overScrollVertical
import com.pos.ui.widget.rememberOverscrollFlingBehavior


@Composable
fun Assets(){
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .overScrollVertical()
            .verticalScroll(state = scrollState,
                flingBehavior = rememberOverscrollFlingBehavior { scrollState })
    ) {
        Text(text = "这是资产")

    }

}

@Composable
fun colorAnimated() {

    val color = remember { Animatable(Color.White) }
    val _transactionY = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(true) {

        _transactionY.animateTo(200f,
            animationSpec = spring(stiffness = StiffnessMediumLow, dampingRatio = Spring.DampingRatioHighBouncy)
        )

        color.animateTo(
            Color.Green,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )


    }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = color.value,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    translationY=_transactionY.value
                }
        ) {}
    }
}