package indi.midreamsheep.app.tre.context.editor.viewmodel

import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.model.shortcut.textfield.TRETextFieldShortcutKeyManager

class ShortcutViewModel(treEditorContext: TREEditorContext) {
    val editorShortcutKeyManager = getBean(TREEditorShortcutKeyManager::class.java)
    val textFieldShortcutKeyManager = getBean(TRETextFieldShortcutKeyManager::class.java)
}