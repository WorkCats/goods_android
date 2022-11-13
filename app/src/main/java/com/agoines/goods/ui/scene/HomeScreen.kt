package com.agoines.goods.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.agoines.goods.ui.vm.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.agoines.goods.data.Good
import com.agoines.goods.data.Screen
import com.agoines.goods.ui.composable.MultiFabItem
import com.agoines.goods.ui.composable.MultiFloatingActionButton
import com.agoines.goods.ui.dialog.AddBottomSheet
import com.agoines.goods.ui.dialog.DelBottomSheet
import com.agoines.goods.ui.dialog.UpdateBottomSheet
import kotlinx.coroutines.launch
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.SquigglyUnderlineSpanPainter
import me.saket.extendedspans.drawBehind
import me.saket.extendedspans.rememberSquigglyUnderlineAnimator
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterialApi::class)
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

    val editGood = remember {
        mutableStateOf(Good("", "", 0u, ""))
    }
    val updateSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val addSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val delSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val delGood = remember {
        mutableStateOf(Good("", "", 0u, ""))
    }

    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    Scaffold(

        scaffoldState = scaffoldState,
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                        .fillMaxWidth()
                        .background(colors.primaryVariant)
                )
                TopAppBar(
                    title = {
                        Text(text = "首页")
                    },
                    modifier = Modifier.padding(
                        WindowInsets.statusBars.only(WindowInsetsSides.Horizontal).asPaddingValues()
                    ),
                    actions = {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "设置",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(16.dp)
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
                    when (item.label) {
                        "扫码" -> navController.navigate(Screen.Camera.route)
                        "手动输入" -> coroutineScope.launch {
                            addSheetState.show()
                        }
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
                key = { good -> good.id },
                contentType = { good -> good.userName }
            ) { good ->
                GoodItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
                    good = good,
                    deleteEvent = {
                        delGood.value = good
                        coroutineScope.launch {
                            delSheetState.show()
                        }
                    },
                    editEvent = {
                        editGood.value = good
                        coroutineScope.launch {
                            updateSheetState.show()
                        }
                    }
                )
            }
        }
    }
    UpdateBottomSheet(
        sheetState = updateSheetState,
        good = editGood.value,
    ) {
        for (i in 0 until goodList.size) {
            if (goodList[i].id == it.id) {
                goodList[i] = it
            }
        }
    }

    AddBottomSheet(
        sheetState = addSheetState
    ) {
        goodList.add(it)
    }

    DelBottomSheet(
        sheetState = delSheetState,
        goodId = delGood.value.id,
        goodName = delGood.value.name,
    ) {
        goodList.remove(delGood.value)
    }
}

@Composable
fun GoodItem(
    modifier: Modifier,
    good: Good,
    deleteEvent: () -> Unit,
    editEvent: () -> Unit
) {


    val archive = SwipeAction(
        icon = {
            Box(modifier = Modifier.padding(end = 16.dp)) {
                Icon(
                    imageVector = Icons.TwoTone.Delete,
                    contentDescription = null,
                    tint = colors.onError
                )
            }

        },
        background = colors.error,
        onSwipe = {
            deleteEvent.invoke()
        }
    )

    val snooze = SwipeAction(
        icon = {
            Box(modifier = Modifier.padding(start = 16.dp)) {
                Icon(
                    imageVector = Icons.TwoTone.Edit,
                    contentDescription = null,
                    tint = colors.onPrimary
                )
            }

        },
        background = colors.secondary,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = good.name,
                modifier = Modifier.align(Alignment.TopStart).padding(vertical = 2.dp),
                style = typography.subtitle1,
                fontWeight = FontWeight(540)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                ExtendedSpansText(
                    text = buildAnnotatedString {
                        if (good.size < 20u) {
                            withStyle(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = colors.error
                                )
                            ) {
                                append("数量：${good.size}")
                            }
                        } else {
                            append("数量：${good.size}")
                        }
                    }
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = colors.primary.copy(alpha = 0.2f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "",
                        Modifier.size(16.dp),
                        tint = colors.primary
                    )
                    Text(
                        text = good.userName,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .align(Alignment.CenterVertically),
                        style = typography.body2,
                        color = colors.primary
                    )
                }


            }
        }
    }
}

@Composable
fun ExtendedSpansText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    val underlineAnimator = rememberSquigglyUnderlineAnimator()
    val extendedSpans = remember {
        ExtendedSpans(
            SquigglyUnderlineSpanPainter(
                width = 2.sp,
                wavelength = 20.sp,
                amplitude = 2.sp,
                bottomOffset = 2.sp,
                animator = underlineAnimator
            )
        )
    }

    Text(
        modifier = modifier.drawBehind(extendedSpans),
        text = remember(text) {
            extendedSpans.extend(text)
        },
        onTextLayout = { result ->
            extendedSpans.onTextLayout(result)
        }
    )
}