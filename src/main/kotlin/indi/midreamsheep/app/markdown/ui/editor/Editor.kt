package indi.midreamsheep.app.markdown.ui.editor

import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.editor.manager.TREFileManager
import indi.midreamsheep.app.markdown.model.shortcut.editor.TREEditorShortcutKeyManager
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.editor.topbar.topBar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            EditorList(context,Modifier.weight(10f), getBean(TREEditorShortcutKeyManager::class.java),listState)
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

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun EditorList(
    context: TREEditorContext,
    modifier: Modifier,
    treEditorShortcutKeyManager: TREEditorShortcutKeyManager,
    listState: LazyListState
) {
    context.informationDisplay.value()
    val stateManager = context.editorFileManager.getStateManager()
    val lineStateList = stateManager.getMarkdownLineStateList()
    var isChange by remember { mutableStateOf(false) }
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
            .onPreviewKeyEvent {
                    keyEvent: KeyEvent ->
                GlobalScope.launch {
                    treEditorShortcutKeyManager.keyEvent(keyEvent)
                    treEditorShortcutKeyManager.execute(context)
                }
                isChange = !isChange
                false
            }
    ) {
        for (markdownLineState in lineStateList) {
            item {
                markdownLineState.line.getComposable(context)()
            }
        }
    }
    LaunchedEffect(isChange){
        val currentMarkdownLineState = stateManager.getCurrentMarkdownLineState() ?: return@LaunchedEffect
        listState.animateScrollToItem(lineStateList.indexOf(currentMarkdownLineState))
    }
    LaunchedEffect(key1 = lineStateList.size) {
        listState.animateScrollToItem(lineStateList.size-1)
    }
}