package indi.midreamsheep.app.markdown.ui.editor.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.editor.manager.MarkdownFileManager
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.toolbar.TopMenuManager

@Composable
fun topBar(
    manager: MarkdownFileManager
){
    val menuManager = getBean(TopMenuManager::class.java)
    for (topFloorBar in menuManager.topFloorBarList) {
        topFloorBar.getComposable(manager.getStateManager())()
    }

}