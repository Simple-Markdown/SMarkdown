package indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.shared.ui.compnent.flomenu.floatingMenu

abstract class TopBarItem {

    fun getComposable(context: TREEditorWindowContext): @Composable () -> Unit {
        return {
            var expanded by remember { mutableStateOf(false) }
            var boxHeight by remember { mutableStateOf(0.dp) }
            val focusRequester = remember { FocusRequester() }
            CustomIntrinsicBox(
                modifier = Modifier.padding(0.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        expanded = it.isFocused
                    }
            ) {
                Text(
                    text = getName(),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .onGloballyPositioned {
                            boxHeight = it.size.height.dp
                        }
                        .clickable {}
                        .padding(8.dp)
                        .layoutId("button")
                    ,
                )
                floatingMenu(
                    expanded,
                    modifier = Modifier,
                    offsetRule = { Pair(0.dp, boxHeight) },
                ){
                    for (subFloorBar in getSubBarList()) {
                        subMenu(context, subFloorBar)
                    }
                }
            }
        }
    }

    @Composable
    fun subMenu(
        context: TREEditorWindowContext,
        subFloorBar: SubBarItem
    ) {
        Box(
            modifier = Modifier.clickable {
                subFloorBar.call(context)
            }) {
            subFloorBar.getComposable().invoke()
        }
    }

    @Composable
    fun CustomIntrinsicBox(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Layout(
            content = content,
            modifier = modifier
        ) { measurables, constraints ->
            // 自定义测量逻辑
            var placeableWidth = 0
            var placeableHeight = 0
            val placeables:MutableList<Placeable> = mutableListOf()
            measurables.forEach { measurable ->
                val placeable = measurable.measure(constraints)
                if (measurable.layoutId == "button") {
                    placeableWidth = placeable.width
                    placeableHeight = placeable.height
                }
                placeables.add(placeable)
            }

            layout(placeableWidth, placeableHeight) {
                for (placeable in placeables) {
                    placeable.placeRelative(0, 0)
                }
            }
        }
    }

    abstract fun getName(): String

    abstract fun getSubBarList(): List<SubBarItem>
}