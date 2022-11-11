package com.agoines.goods.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PickerButton(
    size: Dp,
    drawable: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Icon(
        imageVector = drawable,
        contentDescription = "",
        modifier = Modifier
            .size(size = size)
            .clickable(
                enabled = enabled,
                onClick = { onClick() }
            ).padding(4.dp)
    )
}