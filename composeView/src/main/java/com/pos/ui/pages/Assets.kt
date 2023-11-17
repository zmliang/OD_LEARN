package com.pos.ui.pages

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pos.ui.widget.overScrollHorizontal
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