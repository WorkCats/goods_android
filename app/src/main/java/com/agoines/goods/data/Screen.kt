package com.agoines.goods.data

sealed class Screen(val route: String) {
    object Splash : Screen("Splash")
    object Index : Screen("Index")
    object Login : Screen("Login")
    object Home : Screen("Home")
    object Camera : Screen("Camera")
}