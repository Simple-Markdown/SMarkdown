package indi.midreamsheep.app.tre.shared.ui.compnent.simpleclick

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.simpleClickable(function: () -> Unit): Modifier {
    return clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null, // 这里设置为空，就可以去除水纹特效
        onClick = function
    )
}