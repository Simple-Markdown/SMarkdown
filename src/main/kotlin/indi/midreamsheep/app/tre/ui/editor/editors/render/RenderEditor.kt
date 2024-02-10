package indi.midreamsheep.app.tre.ui.editor.editors.render

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

@Composable
fun renderList(
    context: TREEditorContext,
    modifier: Modifier,
    listState: LazyListState
) {
    val stateManager = context.editorFileManager.getStateManager()
    val lineStateList = stateManager.getMarkdownLineStateList()
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp)
            .onPreviewKeyEvent{
                return@onPreviewKeyEvent context.shortcutAction.textFieldEvent(it)
            }

    ) {
        for (markdownLineState in lineStateList) {
            item {
                markdownLineState.line.getComposable(context)()
            }
        }
    }
   LaunchedEffect(key1 = stateManager.getCurrentMarkdownLineState().value) {
       if (stateManager.getCurrentMarkdownLineState().value == null){
           return@LaunchedEffect
       }
       listState.animateScrollToItem(lineStateList.indexOf(stateManager.getCurrentMarkdownLineState().value!!))
    }
}