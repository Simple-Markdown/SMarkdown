package indi.midreamsheep.app.tre.context.editor.viewmodel

import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.model.listener.shortcut.textfield.TRETextFieldShortcutKeyManager
import indi.midreamsheep.app.tre.tool.ioc.getBean

class ShortcutViewModel(context: TREEditorContext): TREViewModel<TREEditorContext>(context) {
    val editorShortcutKeyManager = getBean(TREEditorShortcutKeyManager::class.java)
    val textFieldShortcutKeyManager = getBean(TRETextFieldShortcutKeyManager::class.java)
}