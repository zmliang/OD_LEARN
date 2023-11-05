package com.example.composeview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

class MyComposeActivity : ComponentActivity() {

    private val next = mutableStateOf("name")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                messageBox()
                HelloContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloContent() {
    var clicked by remember { mutableStateOf(false) }
    val alpha :Float by animateFloatAsState(targetValue = (if (clicked) 0.5f else 1.0f), label = "_alpha")

    Column(modifier = Modifier
        .padding(16.dp)
        .graphicsLayer(alpha = alpha)
        .background(color = Color.Blue)
    ) {
        var name by remember { mutableStateOf("") }
        Text(
            text = "Hello! $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it;clicked = !clicked },
            label = { Text("Name") }
        )
    }
}



@Composable
fun messageBox(){
    Column {
        Text(text = "我是第一行")
        Text(text = "我是第二行")
    }
}