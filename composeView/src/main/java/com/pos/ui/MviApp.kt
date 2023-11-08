package com.pos.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pos.route.Route
import com.pos.route.Route.MainScreen
import com.pos.theme.MviTheme
import com.pos.ui.page.MainViewModel
import com.pos.ui.page.mainScreen
import com.pos.ui.pages.Assets
import com.pos.ui.pages.Discover
import com.pos.ui.pages.Home
import com.pos.ui.pages.Me

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainApp() {
    val navController = rememberNavController()
    var selected by remember { mutableStateOf(0) }
    var items = remember { BottomBarItem.values() }


    MviTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach {
                        NavigationBarItem(
                            label = { it.label },
                            selected = selected == it.ordinal,
                            onClick = {
                                selected = it.ordinal
                                navController.navigate(it.route)
                            },
                            icon = it.icon
                        )
                    }
                }
            },
        ) {
            NavHost(
                modifier=Modifier.padding(it),
                navController = navController,
                startDestination = BottomBarItem.HOME.route
            ) {
//                composable(Route.MainScreen) {
//                    val viewModel = hiltViewModel<MainViewModel>()
//                    mainScreen(viewModel = viewModel)
//                }
                composable(BottomBarItem.HOME.route){
                    Home()
                }
                composable(BottomBarItem.ASSET.route){
                    Assets()
                }
                composable(BottomBarItem.DISCOVER.route){
                    Discover()
                }
                composable(BottomBarItem.ME.route){
                    Me()
                }
            }
        }
    }
}
