package com.agoines.goods.ui.scene

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.CameraViewModel
import com.agoines.goods.utils.isHttp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import kotlinx.coroutines.launch

@Composable
fun CameraScene(
    navController: NavHostController,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(12.dp)
            ) { data ->
                Snackbar(
                    action = {
                        Row(
                            modifier = Modifier.clickable {
                                viewModel.gotoURL(navController, data.message)
                            },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "打开", modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            )
                            Icon(
                                imageVector = Icons.Rounded.OpenInBrowser,
                                contentDescription = ""
                            )
                        }

                    }
                ) {
                    Text(text = data.message)
                }
            }
        },
    ) { padding ->
        AndroidView(
            factory = { context ->
                compoundBarcodeView.apply {
                    CaptureManager(context as Activity, this).apply {
                        initializeFromIntent(context.intent, null)
                        decode()
                    }

                    this.setStatusText("")
                }
            },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) { view ->
            view.decodeContinuous { result ->
                if (scanFlag) return@decodeContinuous
                scanFlag = true
                if (result!!.text.isHttp()) {
                    scope.launch {
                        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                            message = result.text,
                            actionLabel = "前往",
                        )

                        scanFlag = false

                    }
                } else {
                    viewModel.decodeResult(
                        navController,
                        "${result.barcodeFormat.name}_${result.text}"
                    )
                }
            }
            view.resume()

        }
    }
}