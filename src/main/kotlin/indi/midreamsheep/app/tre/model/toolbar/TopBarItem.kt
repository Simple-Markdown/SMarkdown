package indi.midreamsheep.app.tre.model.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

abstract class TopBarItem {

    fun getComposable(context: TREEditorContext):@Composable ()->Unit{
        return {
            var expanded by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.padding(0.dp)
            ) {
                    Text(
                        text = getName(),
                        modifier = Modifier
                            .clickable {
                                expanded = true
                            }
                            .padding(vertical = 3.dp, horizontal = 3.dp)
                        ,
                        style = MaterialTheme.typography.body1,
                    )
                    DropdownMenu(
                        modifier = Modifier,
                        expanded = expanded,
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