package indi.midreamsheep.app.tre.model.editor.line.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.tool.expand.simpleClickable

class TRECoreDisplay(val line:TRECoreLine):Display {
    @Composable
    override fun display() {
        val value = line.render.value.styleText.styleTextTree!!
        Text(
            text = value.build(false),
            style = MaterialTheme.typography.bodyLarge,
            inlineContent = line.render.value.styleText.previewAnnotation,
            modifier = Modifier
                .fillMaxWidth()
                .simpleClickable{
                    line.focus()
                }
            ,
        )
    }
}