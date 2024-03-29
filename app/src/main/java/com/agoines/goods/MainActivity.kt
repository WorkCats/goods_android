package com.agoines.goods

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.agoines.goods.data.Screen
import com.agoines.goods.ui.AppNavHost
import com.agoines.goods.ui.theme.Goods_androidTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            Goods_androidTheme {
                Scaffold { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = colors.background
                    ) {
                        val systemUiController = rememberSystemUiController()
                        val useDarkIcons = isSystemInDarkTheme()
                        DisposableEffect(systemUiController, useDarkIcons) {
                            systemUiController.setStatusBarColor(
                                color = Color.Transparent,
                                darkIcons = false
                            )
                            systemUiController.setNavigationBarColor(
                                color = Color.Transparent,
                                navigationBarContrastEnforced = false,
                                darkIcons = !useDarkIcons
                            )

                            onDispose {}
                        }
                        AppNavHost(
                            viewModel.getStartDestination(this)
                                .collectAsState(initial = Screen.Splash.route)
                        )
                    }

                }
            }
        }
    }
}
