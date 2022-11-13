package com.agoines.goods.ui.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agoines.goods.data.Good
import com.agoines.goods.ui.composable.HorizontalNumberPicker
import com.agoines.goods.ui.vm.UpdateBottomSheetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun UpdateBottomSheet(
    viewModel: UpdateBottomSheetViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    good: Good,
    fromCamera: Boolean = false,
    resultEvent: (Good) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    val bottomSheetState by remember { mutableStateOf(BottomSheetState.BeforeLoading) }

    val coroutineScope = rememberCoroutineScope()

    /**
     * 商品名
     */
    var name by remember { mutableStateOf("") }

    /**
     * 用户名
     */
    var username by remember { mutableStateOf("") }

    /**
     * 数量
     */
    var size by remember { mutableStateOf(0u) }

    LaunchedEffect(sheetState.targetValue) {
        when (sheetState.targetValue) {
            ModalBottomSheetValue.Expanded -> {
                size = good.size
                name = good.name
                username = good.userName

            }

            else ->
                focusManager.clearFocus()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .padding(WindowInsets.imeAnimationTarget.asPaddingValues())
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                Crossfade(targetState = bottomSheetState) { screen ->
                    Column {
                        when (screen) {
                            BottomSheetState.BeforeLoading -> {
                                Text(text = "货物更新", style = MaterialTheme.typography.h6)
                                Text(
                                    text = "更新货物的 ID 为 ${good.id}",
                                    style = MaterialTheme.typography.subtitle1,
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp),

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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp),
                                    value = username,
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
                                        username = it
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
                                        viewModel.updateGood(
                                            good = Good(good.id, name, size, username),
                                            resultEvent = {
                                                resultEvent(Good(good.id, name, size, username))
                                                coroutineScope.launch {
                                                    sheetState.hide()
                                                }
                                            },
                                            {}
                                        )
                                    }) {
                                        Text(text = if (fromCamera) "下一步" else "确定")
                                    }
                                }
                            }

                            BottomSheetState.Loading -> Text("Page A")
                            BottomSheetState.Loaded -> Text("Page B")
                        }
                    }
                }
            }
        }) {
        BackHandler(sheetState.isVisible) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }
}