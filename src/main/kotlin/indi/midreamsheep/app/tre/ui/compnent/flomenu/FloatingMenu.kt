package indi.midreamsheep.app.tre.ui.compnent.flomenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun floatingMenu(
    expand: Boolean,
    modifier: Modifier = Modifier,
    offsetRule: OffsetRule,
    content: @Composable () -> Unit
) {
    var xOffset by remember { mutableStateOf(0.dp) }
    var yOffset by remember { mutableStateOf(0.dp) }
    Box(
        modifier = modifier
            .onGloballyPositioned {
                offsetRule.getXY().let { (x, y) ->
                    xOffset = x
                    yOffset = y
                }
            }
            .offset(x = xOffset, y = yOffset)
            .zIndex(1f)
    ){
        if (expand){
            content()
        }
    }
}

@FunctionalInterface
fun interface OffsetRule {
    fun getXY(): Pair<Dp, Dp>
}