package com.agoines.goods.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowLeft
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalNumberPicker(
    modifier: Modifier = Modifier,
    height: Dp = 36.dp,
    min: UInt = 0u,
    max: UInt = 10u,
    default: UInt = min,
    onValueChange: (UInt) -> Unit = {}
) {
    val number = remember { mutableStateOf(default) }

    Row(
        verticalAlignment = CenterVertically,
        horizontalArrangement = Center,
        modifier = modifier
            .border(
                color = colors.onSurface.copy(alpha = 0.4f),
                width = 1.dp,
                shape = RoundedCornerShape(height / 4)
            )
            .clip(RoundedCornerShape(height / 4))
    ) {
        PickerButton(
            size = height,
            drawable = Icons.Rounded.ArrowLeft,
            enabled = number.value > min,
            onClick = {
                if (number.value > min) number.value--
                onValueChange(number.value)
            }
        )

        Text(
            text = number.value.toString(),
            fontSize = (height.value / 3).sp,
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Max)
                .align(CenterVertically)
        )

        PickerButton(
            size = height,
            drawable = Icons.Rounded.ArrowRight,
            enabled = number.value < max,
            onClick = {
                if (number.value < max) number.value++
                onValueChange(number.value)
            }
        )
    }
}