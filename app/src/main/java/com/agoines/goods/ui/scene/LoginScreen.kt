package com.agoines.goods.ui.scene

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.LoginViewModel

@Composable
fun LoginScene(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val usernameText = remember {
        mutableStateOf("")
    }
    val passwordText = remember {
        mutableStateOf("")
    }
    var autoLogin by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TopAppBar(title = {
            Text(text = "初始化")
        })

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = usernameText.value,
                label = { Text("账号") },
                placeholder = {
                    Text("请输入对应的账号")
                },
                onValueChange = { text ->
                    usernameText.value = text
                })

            TextField(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                value = passwordText.value,
                label = { Text("密码") },
                placeholder = {
                    Text("请输入对应的密码")
                },
                onValueChange = { text ->
                    passwordText.value = text
                })
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "自动登录", modifier = Modifier.align(Alignment.CenterVertically).weight(1f))
                Switch(checked = autoLogin, modifier = Modifier.align(Alignment.CenterVertically), onCheckedChange = { checked ->
                    autoLogin = checked
                })
            }
            
            Button(
                onClick = {
                    viewModel.login(navController, usernameText.value, passwordText.value, autoLogin)
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