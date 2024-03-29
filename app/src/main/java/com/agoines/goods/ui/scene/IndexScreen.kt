package com.agoines.goods.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agoines.goods.ui.vm.IndexViewModel
import androidx.navigation.NavHostController

@Composable
fun IndexScene(
    navController: NavHostController,
    viewModel: IndexViewModel = hiltViewModel()
) {
    val urlText = remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
                .background(colors.primaryVariant)
        )
        TopAppBar(
            title = {
                Text(text = "初始化")
            })

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = urlText.value,
                label = { Text("域名") },
                placeholder = {
                    Text("请输入对应的域名")
                },
                onValueChange = { text ->
                    urlText.value = text
                })

            Button(
                onClick = {
                    viewModel.setUserURL(navController, urlText.value)
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "确定", style = MaterialTheme.typography.button)
            }
        }
    }
}