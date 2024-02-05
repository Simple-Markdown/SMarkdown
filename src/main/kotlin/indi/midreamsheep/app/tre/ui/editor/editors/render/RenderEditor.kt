package indi.midreamsheep.app.tre.ui.editor.editors.render

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyManager

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
            .clickable (
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (stateManager.getCurrentMarkdownLineState() != null) {
                        stateManager.getCurrentMarkdownLineState()!!.line.releaseFocus()
                        Thread.sleep(10)
                    }
                    lineStateList[lineStateList.size-1].line.focus()
                }
            )

    ) {
        for (markdownLineState in lineStateList) {
            item {
                markdownLineState.line.getComposable(context)()
            }
        }
    }
    LaunchedEffect(key1 = lineStateList.size) {
        listState.animateScrollToItem(lineStateList.size-1)
    }
}