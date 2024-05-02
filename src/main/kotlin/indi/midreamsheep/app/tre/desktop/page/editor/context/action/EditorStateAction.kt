package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.shared.api.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel

class EditorStateAction(context: TREEditorContext): TREAction<TREEditorContext>(context) {
    fun sourceMode() {
        context.editorStateViewModel.editorMode.value = EditorStateViewModel.EditorMode.SOURCE
    }

    fun renderMode() {
        context.editorStateViewModel.editorMode.value = EditorStateViewModel.EditorMode.RENDER
    }
}