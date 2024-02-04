package indi.midreamsheep.app.tre.ui.editor

import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.manager.TREFileManager
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.tool.context.getBean
import indi.midreamsheep.app.tre.ui.editor.render.renderList
import indi.midreamsheep.app.tre.ui.editor.render.topbar.topBar
import indi.midreamsheep.app.tre.ui.editor.source.sourceEditor

@Composable
fun editor(
    treFileManager: TREFileManager
){
    if (!treFileManager.isRead()){
        val (result, errorMsg) = treFileManager.read()
        if (!result){
            error(errorMsg)
        }
    }
    val context  = remember { TREEditorContext(treFileManager) }
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        topBar(context)
        Row (Modifier.weight(1f)){
            Spacer(Modifier.weight(1f))

            if (context.isSourceMode.value){
                sourceEditor(context, Modifier.weight(10f), getBean(TREEditorShortcutKeyManager::class.java),listState)
            }else{
                renderList(context, Modifier.weight(10f), getBean(TREEditorShortcutKeyManager::class.java),listState)
            }

            Spacer(Modifier.weight(1f))
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                adapter = ScrollbarAdapter(listState)
            )
        }
        Box(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = context.stateString.value,
                modifier = Modifier.align(Alignment.CenterEnd),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }

}
