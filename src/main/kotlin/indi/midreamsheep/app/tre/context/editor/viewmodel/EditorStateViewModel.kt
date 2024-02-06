package indi.midreamsheep.app.tre.context.editor.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class EditorStateViewModel(context:TREEditorContext): TREViewModel<TREEditorContext>(context) {
    val editorMode:MutableState<EditorMode> = mutableStateOf(EditorMode.RENDER)
    enum class EditorMode{
        RENDER,
        SOURCE
    }
}