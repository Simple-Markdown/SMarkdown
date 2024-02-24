package indi.midreamsheep.app.tre.ui.page.editorpage.editors.source

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.manager.core.source.TRESourceManager

@Composable
fun sourceEditor(
    context: TREEditorContext,
    modifier: Modifier,
    listState: LazyListState
){
    val editorFileManager = context.editorFileManager as TRESourceManager
    val focusRequester = remember { FocusRequester() }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp)
    ) {
        item {
            BasicTextField(
                value = editorFileManager.getState().value,
                onValueChange = {
                    editorFileManager.getState().value = it
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
            )
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}