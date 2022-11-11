package com.agoines.goods.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.agoines.goods.data.Good
import com.agoines.goods.data.Screen
import com.agoines.goods.ui.composable.MultiFabItem
import com.agoines.goods.ui.composable.MultiFloatingActionButton
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun HomeScene(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val goodList = remember {
        mutableStateListOf<Good>()
    }

    LaunchedEffect(null) {
        viewModel.getGoodList().collect {
            if (goodList.isEmpty()) {
                goodList.addAll(it)
            } else if (goodList != it) {
                goodList.clear()
                goodList.addAll(it)
            }
        }
    }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                )
                TopAppBar(
                    title = {
                        Text(text = "首页")
                    },
                    modifier = Modifier.padding(WindowInsets.statusBars.only(WindowInsetsSides.Horizontal).asPaddingValues()),
                    actions = {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "设置",
                            modifier = Modifier.size(56.dp).padding(16.dp)
                        )
                    },
                )
            }
        },

        floatingActionButton = {
            MultiFloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                srcIcon = Icons.Rounded.Add,
                items = arrayListOf(
                    MultiFabItem(
                        label = "手动输入",
                        icon = Icons.Rounded.EditNote
                    ),
                    MultiFabItem(
                        label = "扫码",
                        icon = Icons.Outlined.CameraAlt
                    )
                ),
                onFabItemClicked = { item ->
                    when(item.label){
                        "扫码" -> navController.navigate(Screen.Camera.route)
                        "手动输入" -> navController.navigate(Screen.AddDialog.route)
                    }

                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(
                items = goodList,
                key = { good ->
                    good.id
                },
                contentType = { good ->
                    good.userName
                }
            ) { good ->
                GoodItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
                    good = good,
                    deleteEvent = {
                        viewModel.delGood(goodId = good.id) {
                            goodList.remove(good)
                        }
                    },
                    editEvent = {
                        viewModel.editGood(navController = navController, good = good) {

                        }
                    }
                )
            }
        }
    }
//    Column(
//        Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//    ) {
//        Box(
//            modifier = Modifier
//                .windowInsetsTopHeight(WindowInsets.statusBars)
//                .fillMaxWidth()
//                .background(MaterialTheme.colors.primaryVariant)
//        )
//
//
//        Text(text = text.value)
//
//    }
}

@Composable
fun GoodItem(modifier: Modifier, good: Good, deleteEvent: () -> Unit, editEvent: () -> Unit) {
    val archive = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
            )
        },
        background = Color.Green,
        onSwipe = {
            deleteEvent.invoke()
        }
    )

    val snooze = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
            )
        },
        background = Color.Yellow,
        isUndo = true,
        onSwipe = {
            editEvent.invoke()
        },
    )

    SwipeableActionsBox(
        startActions = listOf(archive),
        endActions = listOf(snooze),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = good.name, style = typography.subtitle2)
            Text(text = good.userName, style = typography.body1)
        }
    }
}