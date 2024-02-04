package indi.midreamsheep.app.tre.ui.editor.source

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyManager

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun sourceEditor(
    context: TREEditorContext,
    modifier: Modifier,
    key: TREEditorShortcutKeyManager,
    listState: LazyListState
){
    val focusRequester = remember { FocusRequester() }
    var content by remember {mutableStateOf(context.editorFileManager.getContent())}
    BasicTextField(
        value = content,
        onValueChange = {
            content = it
        },
        modifier = modifier
            .fillMaxSize().padding(top = 10.dp)
            .onPreviewKeyEvent {
                keyEvent ->
                if (keyEvent.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false
                if (keyEvent.key == Key.Slash && keyEvent.isCtrlPressed) {
                    context.isSourceMode.value = false
                    context.editorFileManager.setContent(content)
                    context.editorFileManager.getStateManager().setCurrentMarkdownLineState(
                        context.editorFileManager.getStateManager().getMarkdownLineStateList().last()
                    )
                    key.clear()
                    return@onPreviewKeyEvent true
                }
                return@onPreviewKeyEvent false
            }
            .focusRequester(focusRequester)
    )
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}