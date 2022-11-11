package com.agoines.goods.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agoines.goods.data.Screen
import com.agoines.goods.ui.dialog.AddDialog
import com.agoines.goods.ui.dialog.UpdateDialog
import com.agoines.goods.ui.scene.CameraScene
import com.agoines.goods.ui.scene.HomeScene
import com.agoines.goods.ui.scene.IndexScene
import com.agoines.goods.ui.scene.LoginScene
import com.agoines.goods.ui.scene.SplashScreen

@OptIn(ExperimentalComposeUiApi::class)
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

        /**
         * 登陆界面
         */
        composable(Screen.Login.route) {
            LoginScene(navController)
        }

        /**
         * 相机界面
         */
        composable(Screen.Camera.route) {
            CameraScene(navController)
        }

        dialog(
            route = Screen.AddDialog.route,
            dialogProperties = DialogProperties(decorFitsSystemWindows = false)
        ) {
            AddDialog(navController)
        }

        dialog(
            route = Screen.AddDialog.route + "/goodId={goodId}",
            arguments = listOf(navArgument("goodId") {}),
            dialogProperties = DialogProperties(decorFitsSystemWindows = false)
        ) { backStackEntry ->
            AddDialog(navController, backStackEntry.arguments!!.getString("goodId", ""))
        }
        dialog(
            route = Screen.UpdateDialog.route + "/goodId={goodId}/goodName={goodName}/userName={userName}/goodSize={goodSize}",
            arguments = listOf(
                navArgument("goodId") {},
                navArgument("goodName") {},
                navArgument("userName") {},
                navArgument("goodSize") {}

            ),
            dialogProperties = DialogProperties(decorFitsSystemWindows = false)
        ) { backStackEntry ->
            UpdateDialog(
                navController,
                backStackEntry.arguments!!.getString("goodId", ""),
                backStackEntry.arguments!!.getString("goodName", ""),
                backStackEntry.arguments!!.getString("userName", ""),
                backStackEntry.arguments!!.getInt("goodSize").toUInt()
            )
        }

    }

}