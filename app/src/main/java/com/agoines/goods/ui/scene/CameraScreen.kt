package com.agoines.goods.ui.scene

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.agoines.goods.data.Good
import com.agoines.goods.ui.dialog.AddBottomSheet
import com.agoines.goods.ui.dialog.UpdateBottomSheet
import com.agoines.goods.ui.vm.CameraViewModel
import com.agoines.goods.utils.isHttp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CameraScene(
    navController: NavHostController,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val compoundBarcodeView = CompoundBarcodeView(LocalContext.current)

    val editGood = remember {
        mutableStateOf(Good("", "", 0u, ""))
    }

    val addGoodId = remember {
        mutableStateOf("")
    }
    val updateSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val addSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
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
                        TextButton(onClick = {
                            viewModel.gotoURL(navController, data.message)
                        }) {
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
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = result.text,
                            actionLabel = "前往",
                        )

                        scanFlag = false

                    }
                } else {
                    val goodId = "${result.barcodeFormat.name}_${result.text}"
                    viewModel.decodeResult(
                        navController,
                        goodId = goodId,
                        updateGoodEvent = { good ->
                            editGood.value = good
                            coroutineScope.launch {
                                updateSheetState.show()
                            }
                        },
                        editGoodEvent = {
                            addGoodId.value = goodId
                            coroutineScope.launch {
                                addSheetState.show()
                            }

                        }
                    )
                }
            }
            view.resume()
        }

        UpdateBottomSheet(
            sheetState = updateSheetState,
            good = editGood.value,
        )

        AddBottomSheet(
            sheetState = addSheetState,
            goodId = addGoodId.value
        )
    }
}