package indi.midreamsheep.app.tre.model.toolbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

abstract class TopBarItem {

    fun getComposable(context: TREEditorContext): @Composable () -> Unit {
        return {
            var expanded by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.padding(0.dp)
            ) {
                TextButton(
                    modifier = Modifier
                        .padding(0.dp)
                        .defaultMinSize(1.dp, 1.dp)
                    ,
                    contentPadding = PaddingValues(5.dp),
                    onClick = {
                        expanded = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                ) {
                    Text(
                        text = getName(),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(0.dp),
                    )
                }
                DropdownMenu(
                    modifier = Modifier,
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    for (subFloorBar in getSubBarList()) {
                        subMenu(context, subFloorBar)
                    }
                }

            }
        }
    }

    @Composable
    fun subMenu(
        context: TREEditorContext,
        subFloorBar: SubBarItem
    ) {
        var isClicked by remember { mutableStateOf(false) }
        if (isClicked) {
            subFloorBar.call(context)
        }
        DropdownMenuItem(
            modifier = Modifier, onClick = {
                isClicked = true
            }) {
            subFloorBar.getComposable()()
        }
    }

    abstract fun getName(): String

    abstract fun getSubBarList(): List<SubBarItem>
}