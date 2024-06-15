package indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.desktop.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorWindowContext

class EditorStateViewModel(context: TREEditorWindowContext): TREViewModel<TREEditorWindowContext>(context) {
    val editorMode:MutableState<EditorMode> = mutableStateOf(EditorMode.RENDER)
    enum class EditorMode{
        RENDER,
        SOURCE
    }
}