package indi.midreamsheep.app.markdown.model.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext

abstract class TopBarItem {

    fun getComposable(context: TREEditorContext):@Composable ()->Unit{
        return {
            var expanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier.padding(0.dp)
            ) {
                    Text(
                        text = getName(),
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp)
                            .clickable {
                                expanded = true
                            }
                        ,
                        style = MaterialTheme.typography.body1,
                    )
                    DropdownMenu(
                        modifier = Modifier
                        ,expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        for (subFloorBar in getSubBarList()) {
                            subMenu(context,subFloorBar)
                        }
                    }

            }
        }
    }

    @Composable
    fun subMenu(
        context: TREEditorContext,
        subFloorBar: SubBarItem
    ){
        var isClicked by remember { mutableStateOf(false) }
        if (isClicked){
            subFloorBar.call(context)
        }
        DropdownMenuItem(
            modifier = Modifier
            ,onClick = {
            isClicked = true
        }) {
            subFloorBar.getComposable()()
        }
    }

    abstract fun getName():String

    abstract fun getSubBarList():List<SubBarItem>
}