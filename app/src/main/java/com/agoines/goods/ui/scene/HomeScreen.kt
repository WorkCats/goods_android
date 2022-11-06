package com.agoines.goods.ui.scene

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.agoines.goods.data.Good
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun HomeScene(navHostController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val goodList = remember {
        mutableStateListOf<Good>()
    }

    val text = remember {
        mutableStateOf("")
    }

    LaunchedEffect(null) {
        viewModel.getGoodList().collect {
            if (goodList.isNotEmpty() && goodList != it) {
                goodList.clear()
                goodList.addAll(it)
            }
        }
    }
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result -> text.value = result.contents?:"" }
    )
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
        )
        TopAppBar(title = {
            Text(text = "首页")
        },
            actions = {
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        scanLauncher.launch(ScanOptions())
                    }
                )
            }
        )

        Text(text = text.value)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                    good = good
                ) {
                    viewModel.delGood(goodId = good.id) {
                        goodList.remove(good)
                    }

                }
            }
        }
    }
}

@Composable
fun GoodItem(modifier: Modifier, good: Good, event: () -> Unit) {
    val archive = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Outlined.Face,
                contentDescription = null,
            )
        },
        background = Color.Green,
        onSwipe = {
            event.invoke()
        }
    )

    val snooze = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Outlined.Face,
                contentDescription = null,
            )
        },
        background = Color.Yellow,
        isUndo = true,
        onSwipe = {

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