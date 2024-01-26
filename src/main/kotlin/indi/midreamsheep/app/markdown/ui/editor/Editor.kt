package indi.midreamsheep.app.markdown.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.editor.manager.MarkdownFileManager
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.ui.editor.topbar.topBar

@Composable
fun editor(
    markdownFileManager: MarkdownFileManager
){
    if (!markdownFileManager.isRead()){
        val (result, errorMsg) = markdownFileManager.read()
        if (!result){
            error(errorMsg)
        }
    }
    val markdownStateManager = markdownFileManager.getStateManager()
    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        topBar(markdownFileManager)
        Row (Modifier.fillMaxSize()){
            Spacer(Modifier.weight(1f))
            EditorList(markdownStateManager,Modifier.weight(10f))
            Spacer(Modifier.weight(1f))
        }
    }

}

@Composable
fun EditorList(
    markdownLineStateManager: MarkdownStateManager,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        for (markdownLineState in markdownLineStateManager.getMarkdownLineStateList()) {
            item {
                markdownLineState.line.getComposable(markdownLineStateManager)()
            }
        }
    }
}