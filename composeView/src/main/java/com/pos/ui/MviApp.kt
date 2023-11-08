package com.pos.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pos.theme.MviTheme
import com.pos.ui.pages.Assets
import com.pos.ui.pages.Discover
import com.pos.ui.pages.Home
import com.pos.ui.pages.Me

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainApp() {
    val navController = rememberNavController()
    var selected by remember { mutableIntStateOf(0) }
    var items = remember { BottomBarItem.values() }


    MviTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.height(60.dp),
                    containerColor = Color.White,
                    tonalElevation = 5.dp,
                    //contentColor = Color("#5D37FF".toColorInt())
                ) {
                    items.forEach {
                        NavigationBarItem(
                            alwaysShowLabel = true,
                            label = { it.label },
                            selected = selected == it.ordinal,
                            onClick = {
                                selected = it.ordinal
                                navController.navigate(it.route)
                            },
                            icon = it.icon,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color("#5D37FF".toColorInt()),
                                selectedTextColor = Color("#5D37FF".toColorInt()),
                                //indicatorColor = Color("#5D37FF".toColorInt()),
                                unselectedIconColor = Color("#141416".toColorInt()),
                                unselectedTextColor = Color("#141416".toColorInt()),
                            )
                        )
                    }
                }
            },
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = BottomBarItem.HOME.route
            ) {
//                composable(Route.MainScreen) {
//                    val viewModel = hiltViewModel<MainViewModel>()
//                    mainScreen(viewModel = viewModel)
//                }
                composable(BottomBarItem.HOME.route) {
                    Home()
                }
                composable(BottomBarItem.ASSET.route) {
                    Assets()
                }
                composable(BottomBarItem.DISCOVER.route) {
                    Discover()
                }
                composable(BottomBarItem.ME.route) {
                    Me()
                }
            }
        }
    }
}
