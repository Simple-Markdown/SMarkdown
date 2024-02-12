package indi.midreamsheep.app.tre.context.editor.viewmodel

import indi.midreamsheep.app.tre.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.model.shortcut.textfield.TRETextFieldShortcutKeyManager

class ShortcutViewModel(context: TREEditorContext): TREViewModel<TREEditorContext>(context) {
    val editorShortcutKeyManager = getBean(TREEditorShortcutKeyManager::class.java)
    val textFieldShortcutKeyManager = getBean(TRETextFieldShortcutKeyManager::class.java)
}