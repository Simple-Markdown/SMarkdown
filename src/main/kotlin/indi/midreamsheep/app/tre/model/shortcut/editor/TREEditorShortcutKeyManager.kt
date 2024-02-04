package indi.midreamsheep.app.tre.model.shortcut.editor

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.tre.model.shortcut.TREEditorShortcutKeyManagerAbstract
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREEditorShortcutKeyManager: TREEditorShortcutKeyManagerAbstract() {

    @ListInjector(target = "editorShortcutKeys")
    val keyActions = mutableListOf<TREShortcutKey>()
    override fun getActions(): MutableList<TREShortcutKey> {
        return keyActions
    }
}