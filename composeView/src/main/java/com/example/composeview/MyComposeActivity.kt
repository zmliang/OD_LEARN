package com.example.composeview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeview.pages.Assets
import com.example.composeview.pages.Discover
import com.example.composeview.pages.Home
import com.example.composeview.pages.Me

class MyComposeActivity : ComponentActivity() {

    private val next = mutableStateOf("name")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var selected by remember { mutableStateOf(0) }
    var items = remember { BottomBarItem.values() }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "首页") },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Green
                        )
                    )
                },
                bottomBar = {
                    NavigationBar {

                        items.forEach {
                            NavigationBarItem(
                                label = { it.label },
                                selected = selected == it.ordinal,
                                onClick = {
                                    selected = it.ordinal
                                },
                                icon = it.icon
                            )
                        }
                    }
                },
                containerColor = Color.Red
            ) {
                Box(modifier = Modifier.padding(it)) {
                    when (selected) {
                        0 -> Home()
                        1 -> Assets()
                        2 -> Discover()
                        3 -> Me()
                    }
                }
            }
        }
    }

}
