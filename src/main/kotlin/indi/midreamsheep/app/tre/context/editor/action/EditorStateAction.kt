package indi.midreamsheep.app.tre.context.editor.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.context.editor.viewmodel.EditorStateViewModel

class EditorStateAction(context:TREEditorContext): TREAction<TREEditorContext>(context) {
    fun sourceMode() {
        context.editorStateViewModel.editorMode.value = EditorStateViewModel.EditorMode.SOURCE
    }

    fun renderMode() {
        context.editorStateViewModel.editorMode.value = EditorStateViewModel.EditorMode.RENDER
    }

    fun toggleMode() {
        if (context.editorStateViewModel.editorMode.value == EditorStateViewModel.EditorMode.RENDER) {
            sourceMode()
        } else {
            renderMode()
        }
    }
}