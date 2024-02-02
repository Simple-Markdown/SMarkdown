package indi.midreamsheep.app.markdown.model.shortcut.editor

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.model.shortcut.TREEditorShortcutKeyManagerAbstract
import indi.midreamsheep.app.markdown.model.shortcut.TREShortcutKey
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREEditorShortcutKeyManager: TREEditorShortcutKeyManagerAbstract() {

    @ListInjector(target = "editorShortcutKeys")
    val keyActions = mutableListOf<TREShortcutKey>()
    override fun getActions(): MutableList<TREShortcutKey> {
        return keyActions
    }
}