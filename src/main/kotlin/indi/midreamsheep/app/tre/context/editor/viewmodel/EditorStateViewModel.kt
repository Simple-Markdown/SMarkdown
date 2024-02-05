package indi.midreamsheep.app.tre.context.editor.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class EditorStateViewModel(val context:TREEditorContext) {
    val editorMode:MutableState<EditorMode> = mutableStateOf(EditorMode.RENDER)
    enum class EditorMode{
        RENDER,
        SOURCE
    }
}