package indi.midreamsheep.app.tre.model.toolbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

abstract class SubBarItem {
    fun getComposable():@Composable ()->Unit {
        return {
            Row(
                modifier = Modifier
            ) {
                Text(
                    color = Color.Black,
                    text = getName(),
                    style = MaterialTheme.typography.body1,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = getShortcutKey(),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray
                )
            }
        }
    }

    abstract fun call(context: TREEditorContext)

    protected abstract fun getName():String
    protected open fun getShortcutKey():String {
        return ""
    }
}