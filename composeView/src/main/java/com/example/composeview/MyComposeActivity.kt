package com.example.composeview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MyComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text(text = "A long time ago")

            messageBox()
        }
    }
}



@Composable
fun messageBox(){
    Column {
        Text(text = "我是第一行")
        Text(text = "我是第二行")
    }
}