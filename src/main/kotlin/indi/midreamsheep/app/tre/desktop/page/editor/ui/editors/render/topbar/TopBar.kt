package indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindowContext
import indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.TopMenuManager
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean

@Composable
fun topBar(){
    val list = getBean(TopMenuManager::class.java).topFloorBarList
    val context = TRELocalEditorWindowContext.LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth().background(Color(0xfff2f2f2)).zIndex(5f)
    ) {
        for ((index, topFloorBar) in list.withIndex()) {
            topFloorBar.getComposable(context)()
            if (index != list.size - 1) {
                Spacer(modifier = Modifier.width(1.dp))
            }
        }
    }
}