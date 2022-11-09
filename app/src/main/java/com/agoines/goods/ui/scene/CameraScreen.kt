package com.agoines.goods.ui.scene

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.agoines.goods.data.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
fun CameraScene(
    navController: NavHostController
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false,
            isNavigationBarContrastEnforced = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            navigationBarContrastEnforced = false,
            darkIcons = !useDarkIcons
        )

        onDispose {

        }
    }
    var scanFlag by remember {
        mutableStateOf(false)
    }

    AndroidView(
        factory = { context ->
            CompoundBarcodeView(context).apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("")
                capture.decode()
                this.decodeContinuous { result ->
                    if (scanFlag) return@decodeContinuous

                    result?.let {
                        Log.d("结果", it.text + it.barcodeFormat.name, )
                        navController.navigate(Screen.AddDialog.route)
                        scanFlag = true
                    }


                }
                this.resume()
            }
        },
        modifier = Modifier
    )
}