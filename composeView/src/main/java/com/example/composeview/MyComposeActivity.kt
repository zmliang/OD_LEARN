package com.example.composeview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        Text(
            text = "Hello! $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
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