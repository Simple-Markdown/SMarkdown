package indi.midreamsheep.app.tre.model.listener.shortcut.textfield

import indi.midreamsheep.app.tre.model.listener.shortcut.TREEditorShortcutKeyManagerAbstract
import indi.midreamsheep.app.tre.model.listener.shortcut.TREKeyboardKeyManager
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyHandler
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRETextFieldShortcutKeyManager: TREEditorShortcutKeyManagerAbstract() {

    @ListInjector(target = "TextFieldShortcutKeys")
    private val keyActions: MutableList<TREShortcutKeyHandler> = mutableListOf()

    @Injector
    private val keyManager: TREKeyboardKeyManager? = null

    override fun manager() = keyManager!!

    override fun getActions(): MutableList<TREShortcutKeyHandler> {
        return keyActions
    }
}