package com.agoines.goods.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.agoines.goods.ui.composable.HorizontalNumberPicker

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
        Text(text = "货物更新", style = MaterialTheme.typography.h6)
        Text(
            text = "更新的货物ID 为 $goodId",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 10.dp)
        )
        OutlinedTextField(
            modifier = Modifier.padding(top = 20.dp),

            value = name.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Label,
                    contentDescription = ""
                )
            },
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = ""
                )
            },
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
            Text(text = "数量", modifier = Modifier.weight(1f))
            HorizontalNumberPicker(
                min = 1u,
                max = 10000u,
                default = size,
                onValueChange = {
                    size = it
                }
            )
        }

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
            Button(onClick = {
            }, Modifier.padding(end = 4.dp)) {
                Text(text = if (fromCamera) "下一个" else "确定")
            }
        }
    }
}