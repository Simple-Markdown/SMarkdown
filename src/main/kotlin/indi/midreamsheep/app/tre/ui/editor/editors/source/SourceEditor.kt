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
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts.ToggleTheEditorShortcut

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun sourceEditor(
    context: TREEditorContext,
    modifier: Modifier,
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
                if (keyEvent.key == Key.Slash && keyEvent.isCtrlPressed) {
/*                    context.editorStateAction.renderMode()
                    context.editorFileManager.setContent(content)
                    context.editorFileManager.getStateManager().setCurrentMarkdownLineState(
                        context.editorFileManager.getStateManager().getMarkdownLineStateList().last()
                    )*/
                    getBean(ToggleTheEditorShortcut::class.java).action(context)
                    return@onPreviewKeyEvent true
                }
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