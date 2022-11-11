package com.agoines.goods.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UpdateDialog(
    navController: NavHostController,
    goodId: String,
    goodName: String,
    userName: String,
    goodSize: UInt,
    fromCamera: Boolean = false
) {

    /**
     * 商品名
     */
    val name = remember { mutableStateOf(goodName) }

    /**
     * 用户名
     */
    val username = remember { mutableStateOf(userName) }

    /**
     * 数量
     */
    var size by remember { mutableStateOf(goodSize) }

    Column(
        modifier = Modifier
            .imePadding()
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 20.dp)

    ) {
        Text(text = "货物更新")
        Text(
            text = "更新的货物ID 为 $goodId",
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 10.dp)
        )

        OutlinedTextField(modifier = Modifier.padding(top = 20.dp),
            value = name.value,
            label = { Text("名称") },
            placeholder = {
                Text("请输入货物名称")
            },
            onValueChange = {
                name.value = it
            })

        OutlinedTextField(
            modifier = Modifier.padding(top = 20.dp),
            value = username.value,
            label = { Text("所属用户") },
            placeholder = {
                Text("请输入货物所属用户的信息")
            },
            onValueChange = {
                username.value = it
            }
        )

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "取消")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = if (fromCamera) "确定" else "下一个")
            }
        }

    }
}