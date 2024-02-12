package indi.midreamsheep.app.tre.ui.editor.editors.source

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
import indi.midreamsheep.app.tre.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts.ToggleTheEditorShortcut

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun sourceEditor(
    context: TREEditorContext,
    modifier: Modifier,
    listState: LazyListState
){
    context.editorFileManager.getContent().value = context.editorFileManager.getSourceContent()
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = context.editorFileManager.getContent().value,
        onValueChange = {
            context.editorFileManager.getContent().value = it
        },
        modifier = modifier
            .fillMaxSize().padding(top = 10.dp)
            .onPreviewKeyEvent {
                keyEvent ->
                context.shortcutAction.updateTextFieldEvent(keyEvent)
                return@onPreviewKeyEvent false
            }
            .focusRequester(focusRequester)
    )
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}