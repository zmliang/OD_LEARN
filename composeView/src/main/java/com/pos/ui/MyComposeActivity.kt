package com.pos.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pos.ui.pages.Assets
import com.pos.ui.pages.Discover
import com.pos.ui.pages.Home
import com.pos.ui.pages.Me
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


@AndroidEntryPoint
class MyComposeActivity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob()+Dispatchers.Main)

    private val next = mutableStateOf("name")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainApp()
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
