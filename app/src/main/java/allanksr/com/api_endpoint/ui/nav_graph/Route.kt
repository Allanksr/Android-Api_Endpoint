package  allanksr.com.api_endpoint.ui.nav_graph

sealed class Route(val route: String) {
    object SplashScreen : Route("splash_screen")
    object MainScreen : Route("main_screen")
}