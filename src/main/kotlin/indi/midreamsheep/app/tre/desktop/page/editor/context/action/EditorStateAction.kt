package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.desktop.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel

class EditorStateAction(context: TREEditorWindowContext): TREAction<TREEditorWindowContext>(context) {
    fun sourceMode() {
        context.editorStateViewModel.editorMode.value = EditorStateViewModel.EditorMode.SOURCE
    }

    fun renderMode() {
        context.editorStateViewModel.editorMode.value = EditorStateViewModel.EditorMode.RENDER
    }
}