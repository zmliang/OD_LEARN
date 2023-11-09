package com.pos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pos.R
import com.pos.theme.MviTheme
import com.pos.ui.pages.Assets
import com.pos.ui.pages.Discover
import com.pos.ui.pages.Home
import com.pos.ui.pages.Me
import com.pos.ui.widget.CustomNavBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun mainApp() {
    val navController = rememberNavController()
    var selected by remember { mutableIntStateOf(0) }
    var items = remember { BottomBarItem.values() }


    val state = navController.currentBackStackEntryAsState()
    MviTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = Color.White,
                    tonalElevation = 5.dp
                ) {
                    items.forEach {
                        val isSelected = state.value?.destination?.route == it.route
                        NavigationBarItem(
                            modifier = Modifier,
                                    selected = isSelected,
                            onClick = {
                                  navController.navigate(it.route)
                            },
                            icon = {
                                Column(
                                    modifier = Modifier.background(Color.Transparent).wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    BadgedBox(badge =  {
                                        Text(text = "")
                                    }) {
                                        Icon(painter = painterResource(it.iconSrc), contentDescription = null)
                                    }
                                    Text(text = it.label, color = if (isSelected)Color("#5D37FF".toColorInt())else Color.Gray)
                                }
                            },
                            colors = NavigationBarItemDefaults
                                .colors(
                                    selectedIconColor = Color("#5D37FF".toColorInt()),
                                    indicatorColor = Color.White
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
