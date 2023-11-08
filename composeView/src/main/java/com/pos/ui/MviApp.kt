package com.pos.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun mainApp(){
    val navController = rememberNavController()

    MviTheme {
        Scaffold (
            content = {
                NavHost(navController = navController, startDestination = Route.MainScreen){
                    mainScreenRoute(navController)
                }
            }
        )
    }
}


private fun NavGraphBuilder.mainScreenRoute(navController: NavController) {
    composable(Route.MainScreen) {
        val viewModel = hiltViewModel<MainViewModel>()
        mainScreen(viewModel = viewModel)
    }
}