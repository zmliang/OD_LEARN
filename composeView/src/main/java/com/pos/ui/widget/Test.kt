package com.pos.ui.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.currentComposer


@Composable
fun test(){
    val local = compositionLocalOf { 1 }

    Text(text = "aaa")

}