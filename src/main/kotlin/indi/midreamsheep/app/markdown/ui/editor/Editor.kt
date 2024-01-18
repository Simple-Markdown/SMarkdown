package indi.midreamsheep.app.markdown.ui.editor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.editor.manager.MarkdownFileManager
import indi.midreamsheep.app.markdown.editor.manager.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean
import kotlin.math.log

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

    Row (Modifier.fillMaxSize()){
        Spacer(Modifier.weight(1f))
        EditorList(markdownStateManager,Modifier.weight(10f))
        Spacer(Modifier.weight(1f))
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
                editorLine(markdownLineState,markdownLineStateManager)
            }
        }
    }
}

@Composable
fun editorLine(
    markdownLineState: MarkdownLineState,
    markdownLineStateManager: MarkdownStateManager
){
    if (markdownLineState.isFocused.value){
        markdownLineState.inputComposable(markdownLineState,markdownLineStateManager)
        return
    }
    markdownLineState.previewComposable(markdownLineState,markdownLineStateManager)
}