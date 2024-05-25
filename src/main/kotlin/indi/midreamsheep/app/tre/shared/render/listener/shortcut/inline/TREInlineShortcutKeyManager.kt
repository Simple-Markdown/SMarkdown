package indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline

import indi.midreamsheep.app.tre.model.listener.shortcut.TREKeyboardKeyManager
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyHandler
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.tre.shared.render.listener.shortcut.TREShortcutKeyManagerAbstract
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TREInlineShortcutKeyManager: TREShortcutKeyManagerAbstract() {

    @ListInjector(target = "TextFieldShortcutKeys")
    private val keyActions: MutableList<TREShortcutKeyHandler> = mutableListOf()

    @Injector
    private val keyManager: TREKeyboardKeyManager? = null

    override fun manager() = keyManager!!

    override fun getActions(): MutableList<TREShortcutKeyHandler> {
        return keyActions
    }
}