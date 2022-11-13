package com.agoines.goods.ui.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agoines.goods.data.Good
import com.agoines.goods.ui.composable.HorizontalNumberPicker
import com.agoines.goods.ui.vm.AddBottomSheetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddBottomSheet(
    viewModel: AddBottomSheetViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    goodId: String = "",
    resultEvent: (Good) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    /**
     * 商品名
     */
    var name by remember { mutableStateOf("") }

    /**
     * id
     */
    var id by remember { mutableStateOf("") }
    var idError by remember { mutableStateOf(false) }

    /**
     * 用户名
     */
    var userName by remember { mutableStateOf("") }

    /**
     * 数量
     */
    var size by remember { mutableStateOf(10u) }


    LaunchedEffect(sheetState.targetValue) {
        when (sheetState.targetValue) {

            ModalBottomSheetValue.Expanded -> {
                name = ""
                id = goodId
                userName = ""
                size = 10u
            }

            else -> {

            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .imePadding()
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                Text(text = "货物添加", style = typography.h6)

                OutlinedTextField(modifier = Modifier.padding(top = 20.dp),
                    isError = idError,
                    value = id,
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
                        id = it
                    })

                OutlinedTextField(
                    modifier = Modifier.padding(top = 20.dp),
                    value = name,
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
                        name = it
                    })

                OutlinedTextField(
                    modifier = Modifier.padding(top = 20.dp),
                    value = userName,
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
                        userName = it
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
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(text = "取消")
                    }
                    Button(onClick = {
                        viewModel.hasGood(
                            id,
                            resultEvent = {
                                idError = it
                                if (!idError) {
                                    viewModel.addGood(
                                        good = Good(id, name, size, userName),
                                        resultEvent = {
                                            resultEvent(Good(id, name, size, userName))
                                            coroutineScope.launch {
                                                sheetState.hide()
                                            }
                                        },
                                        {}
                                    )
                                }
                            },
                            throwEvent = {

                            }
                        )

                    }) {
                        Text(text = if (goodId == "") "确定" else "下一个")
                    }
                }

            }
        }
    ) {
        BackHandler(sheetState.isVisible) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }
}