package com.agoines.goods.ui.scene

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
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
    val context = LocalContext.current
    var scanFlag by remember {
        mutableStateOf(false)
    }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("")
            capture.decode()
            this.decodeContinuous { result ->
                if(scanFlag){
                    return@decodeContinuous
                }
                scanFlag = true
                result.text?.let { _ ->
                    //Do something and when you finish this something
                    //put scanFlag = false to scan another item
                    scanFlag = false
                }
                //If you don't put this scanFlag = false, it will never work again.
                //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning

            }
        }
    }

    AndroidView(
        modifier = Modifier,
        factory = { compoundBarcodeView },
    )
}