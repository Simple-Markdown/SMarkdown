package indi.midreamsheep.app.markdown.ui.editor.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.toolbar.TopMenuManager
import indi.midreamsheep.app.markdown.tool.context.getBean

@Composable
fun topBar(
    context: TREEditorContext
){
    val list = getBean(TopMenuManager::class.java).topFloorBarList
    Row(
        modifier = Modifier.fillMaxWidth().background(Color(0xfff2f2f2))
    ) {
        for ((index, topFloorBar) in list.withIndex()) {
            topFloorBar.getComposable(context)()
            if (index != list.size - 1) {
                Spacer(modifier = Modifier.width(1.dp))
            }
        }
    }
}