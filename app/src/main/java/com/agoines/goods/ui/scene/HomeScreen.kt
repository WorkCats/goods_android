package com.agoines.goods.ui.scene

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScene(navHostController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(null) {
        viewModel.getGoodList()
    }
}