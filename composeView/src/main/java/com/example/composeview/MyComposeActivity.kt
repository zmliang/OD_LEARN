package com.example.composeview

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
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
import com.example.composeview.pages.Assets
import com.example.composeview.pages.Discover
import com.example.composeview.pages.Home
import com.example.composeview.pages.Me
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyComposeActivity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob()+Dispatchers.Main)

    private val next = mutableStateOf("name")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            Log.i("ZML","global scope start")
            test1()
            test2()

            var deferred:Deferred<String>
            withContext(Dispatchers.IO){
                deferred =async {
                    Log.i("ZML","线程名="+Thread.currentThread().name)
                    delay(10000)
                    "大傻叉"
                }
            }

            val result = deferred.await()

            Log.i("ZML","this is in global scope==$result")
        }
        Log.i("ZML","=========================")
    }

    suspend fun test1(){
        delay(10000)
        Log.i("ZML","this is test1")
    }

    suspend fun test2(){
        delay(10000)
        Log.i("ZML","this is test2")
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
