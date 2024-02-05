package indi.midreamsheep.app.tre.model.shortcut.editor

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.tre.model.shortcut.TREEditorShortcutKeyManagerAbstract
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyHandler
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREEditorShortcutKeyManager: TREEditorShortcutKeyManagerAbstract() {

    @ListInjector(target = "EditorShortcutKeys")
    private val keyActions: MutableList<TREShortcutKeyHandler> = mutableListOf()

    override fun getActions(): MutableList<TREShortcutKeyHandler> {
        return keyActions
    }
}