package indi.midreamsheep.app.markdown.toolbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

abstract class TopFloorBar {

    fun getComposable(manager:MarkdownStateManager):@Composable ()->Unit{
        return {
            var expanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier.padding(0.dp)
            ) {
                    Button(
                        onClick = { expanded = true },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text(
                            text = getName(),
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        for (subFloorBar in getSubBarList()) {
                            subMenu(manager,subFloorBar)
                        }
                    }

            }
        }
    }

    @Composable
    fun subMenu(
        manager: MarkdownStateManager,
        subFloorBar: SubFloorBar
    ){
        var isClicked by remember { mutableStateOf(false) }
        if (isClicked){
            subFloorBar.call(manager)
        }
        DropdownMenuItem(onClick = {
            isClicked = true
        }) {
            Text(
                text = subFloorBar.getName()
            )
        }
    }

    abstract fun getName():String

    abstract fun getSubBarList():List<SubFloorBar>
}