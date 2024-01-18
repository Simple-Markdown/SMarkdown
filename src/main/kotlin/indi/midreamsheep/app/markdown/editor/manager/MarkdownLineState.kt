package indi.midreamsheep.app.markdown.editor.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import indi.midreamsheep.app.markdown.ui.editor.editorInput
import indi.midreamsheep.app.markdown.ui.editor.editorPreview

class MarkdownLineState {
    var content:MutableState<String> = mutableStateOf("")

    var focusRequester:FocusRequester = FocusRequester()
    var isFocused:MutableState<Boolean> = mutableStateOf(false)

    var isInit:MutableState<Boolean> = mutableStateOf(false)

    var type:String = "SingleLineText"
    var typeData:String = ""
    var inputComposable:@Composable (MarkdownLineState,MarkdownStateManager)->Unit = {
        state,stateList->
        editorInput(state,stateList)
    }
    var previewComposable:@Composable (MarkdownLineState,MarkdownStateManager)->Unit = {
        state,stateList->
        editorPreview(state,stateList)
    }
}