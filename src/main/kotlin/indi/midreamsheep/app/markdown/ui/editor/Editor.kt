package indi.midreamsheep.app.markdown.ui.editor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.editor.manager.TREFileManager
import indi.midreamsheep.app.markdown.ui.editor.topbar.topBar

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
    val context  = TREEditorContext(treFileManager)
    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        topBar(context)
        Row (Modifier.fillMaxSize()){
            Spacer(Modifier.weight(1f))
            EditorList(context,Modifier.weight(10f))
            Spacer(Modifier.weight(1f))
        }
    }

}

@Composable
fun EditorList(
    context: TREEditorContext,
    modifier: Modifier
) {
    val stateManager = context.editorFileManager.getStateManager()
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = 10.dp)
            .clickable (
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (stateManager.getCurrentMarkdownLineState() != null) {
                        stateManager.getCurrentMarkdownLineState()!!.line.releaseFocus()
                        Thread.sleep(10)
                    }
                    stateManager.getMarkdownLineStateList()[stateManager.getMarkdownLineStateList().size-1].line.focus()
                }
            )
    ) {
        for (markdownLineState in stateManager.getMarkdownLineStateList()) {
            item {
                markdownLineState.line.getComposable(context)()
            }
        }
    }
}