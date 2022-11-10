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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.CameraViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
fun CameraScene(
    navController: NavHostController,
    viewModel: CameraViewModel = hiltViewModel()
) {

    val compoundBarcodeView = CompoundBarcodeView(LocalContext.current)
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
            compoundBarcodeView.pauseAndWait()
        }
    }

    var scanFlag by remember {
        mutableStateOf(false)
    }

    AndroidView(
        factory = { context ->
            compoundBarcodeView.apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("")
                capture.decode()
                this.decodeContinuous { result ->
                    if (scanFlag) return@decodeContinuous

                    result?.let {
                        viewModel.decodeResult(navController, "${it.barcodeFormat.name}_${it.text}")
                        scanFlag = true
                    }

                }
                this.resume()

            }
        },
        modifier = Modifier
    )


}