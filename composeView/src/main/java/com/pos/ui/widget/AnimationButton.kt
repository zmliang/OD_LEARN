package com.pos.ui.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun AnimationButton(){
    var zoomIn by remember { mutableStateOf(false) }

    val radius by animateDpAsState(targetValue = if (zoomIn) 85.dp else 30.dp, label = "",
        animationSpec = infiniteRepeatable(tween(durationMillis = 200),repeatMode = RepeatMode.Reverse)
    )

    Box(modifier = Modifier
        .size(100.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(Color.Green)
        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
            zoomIn = !zoomIn
        },
        contentAlignment = Alignment.Center
        ) {

        Box(modifier = Modifier
            .size(radius)
            .clip(RoundedCornerShape(radius/2))
            .background(Color.White)
        ){

        }
    }

}