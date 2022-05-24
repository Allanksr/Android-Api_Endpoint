package  allanksr.com.api_endpoint.ui.nav_graph

import allanksr.com.api_endpoint.ui.screens.MainScreen
import allanksr.com.api_endpoint.ui.screens.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route
    ) {
        composable(
            route = Route.SplashScreen.route,
            content = {
                SplashScreen(
                    navController = navController
                )
            }
        )
        composable(
            route = Route.MainScreen.route,
            content = {
                MainScreen()
            }
        )
    }
}