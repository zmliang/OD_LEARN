package com.pos.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.pos.ui.widget.InfiniteCarousel
import com.pos.ui.widget.Resident

@Composable
fun Home(){

    Column {

        InfiniteCarousel()

        Resident()
    }


}