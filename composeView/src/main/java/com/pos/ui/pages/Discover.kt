package com.pos.ui.pages

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.paging.LOGGER

@Composable
fun Discover(){

    subComposeTest {
        Text(text = "这是发现页")
    }
}


@Composable
fun subComposeTest(
    content: @Composable () -> Unit
){
    SubcomposeLayout{constraints: Constraints ->
        var contentPlaceable = subcompose("_content",content).first().measure(constraints)
        Log.e("zml","placeable=${contentPlaceable.width} , ${contentPlaceable.height}")
        layout(
            contentPlaceable.width,
            contentPlaceable.height
        ){
            contentPlaceable.placeRelative(100,200)
        }
    }
}