package com.agoines.goods.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agoines.goods.data.Screen
import com.agoines.goods.ui.scene.CameraScene
import com.agoines.goods.ui.scene.HomeScene
import com.agoines.goods.ui.scene.IndexScene
import com.agoines.goods.ui.scene.LoginScene
import com.agoines.goods.ui.scene.SplashScreen

@Composable
fun AppNavHost(
    startDestination: State<String>,
    navController: NavHostController = rememberNavController()
) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = startDestination.value
        ) {


            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }
            composable(Screen.Home.route) {


                HomeScene(navController)
            }
            composable(Screen.Index.route) {
                IndexScene(navController)
            }
            composable(Screen.Login.route) {
                LoginScene(navController)
            }
            composable(Screen.Camera.route) {
                CameraScene(navController)
            }
        }

}