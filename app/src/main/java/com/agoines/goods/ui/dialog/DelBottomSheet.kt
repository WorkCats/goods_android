package com.agoines.goods.ui.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import com.agoines.goods.ui.vm.DelBottomSheetViewModel
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DelBottomSheet(
    viewModel: DelBottomSheetViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    goodId: String,
    goodName: String,
    resultEvent: () -> Unit
) {
    var bottomSheetState by remember { mutableStateOf(BottomSheetState.BeforeLoading) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(sheetState.targetValue) {
        if (sheetState.targetValue == ModalBottomSheetValue.Expanded) {
            bottomSheetState = BottomSheetState.BeforeLoading
        }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier.imePadding().padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                Crossfade(targetState = bottomSheetState) { screen ->
                    Column {
                        when (screen) {
                            BottomSheetState.BeforeLoading -> {

                                Text(text = "货物删除", style = MaterialTheme.typography.h6)
                                Text(
                                    text = "删除货物为 $goodName，ID 为 $goodId",
                                    style = MaterialTheme.typography.subtitle1,
                                    modifier = Modifier.padding(top = 10.dp)
                                )

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
                                        bottomSheetState = BottomSheetState.Loading
                                        viewModel.delGood(
                                            goodId = goodId,
                                            resultEvent = {
                                                resultEvent()
                                                coroutineScope.launch {
                                                    sheetState.hide()
                                                }
                                            },
                                            throwEvent = {
                                                bottomSheetState = BottomSheetState.Loaded
                                            }
                                        )
                                    }) {
                                        Text(text = "确定")
                                    }
                                }

                            }

                            BottomSheetState.Loading -> {
                                Column {
                                    Text(text = "加载中", style = MaterialTheme.typography.h6)
                                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                                }
                            }

                            BottomSheetState.Loaded -> {
                                Column {
                                    Text(text = "删除失败", style = MaterialTheme.typography.h6)
                                    Text(
                                        text = "删除货物为 $goodName，ID 为 $goodId",
                                        style = MaterialTheme.typography.subtitle1,
                                        modifier = Modifier.padding(top = 10.dp)
                                    )
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
                                            bottomSheetState = BottomSheetState.Loading
                                            viewModel.delGood(
                                                goodId = goodId,
                                                resultEvent = {
                                                    resultEvent()
                                                    coroutineScope.launch {
                                                        sheetState.hide()
                                                    }
                                                },
                                                throwEvent = {
                                                    bottomSheetState = BottomSheetState.Loaded
                                                }
                                            )
                                        }) {
                                            Text(text = "重试")
                                        }
                                    }
                                }
                            }
                        }
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