package com.example.composeview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
                //第一个 padding(8.dp) 跟 margin 效果相同
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.Cyan, RoundedCornerShape(12.dp))
                        .padding(6.dp)
                ) {
                    Box(
                        Modifier
                            .background(Color.Red)
                            .size(100.dp, 80.dp)
                    )
                    Spacer(
                        Modifier
                            .width(20.dp)
                    )
                    Box(
                        Modifier
                            .background(Color.Magenta)
                            .height(60.dp)
                            .weight(1f)
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloContent() {
    var clicked by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        targetValue = (if (clicked) 0.5f else 1.0f),
        label = "_alpha"
    )

    Column(
        modifier = Modifier
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
fun messageBox() {
    Column {
        Text(text = "我是第一行")
        Text(text = "我是第二行")
    }
}