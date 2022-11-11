package com.agoines.goods.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Verified
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
fun AddDialog(
    navController: NavHostController,
    goodId: String = ""
) {
    /**
     * 商品名
     */
    val name = remember { mutableStateOf("") }

    /**
     * id
     */
    val id = remember { mutableStateOf(goodId) }

    /**
     * 用户名
     */
    val userName = remember { mutableStateOf("") }

    /**
     * 数量
     */
    var size by remember { mutableStateOf(10u) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        Text(text = "货物添加", style = typography.h6)

        OutlinedTextField(modifier = Modifier.padding(top = 20.dp),
            value = id.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Verified,
                    contentDescription = ""
                )
            },
            label = { Text("ID") },
            placeholder = {
                Text("请输入货物对应的ID")
            },
            onValueChange = {
                id.value = it
            })

        OutlinedTextField(modifier = Modifier.padding(top = 20.dp),
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
            value = userName.value,
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
                userName.value = it
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
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 4.dp)) {
                Text(text = "取消")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = if (goodId == "") "确定" else "下一个")
            }
        }

    }
}