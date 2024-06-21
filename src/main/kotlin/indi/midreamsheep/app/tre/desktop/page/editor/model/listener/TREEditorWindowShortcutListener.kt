package indi.midreamsheep.app.tre.desktop.page.editor.model.listener

import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker

interface TREEditorWindowShortcutListener {
    fun isEnable(context: TREEditorWindowContext): Boolean

    fun getKeys():List<TREShortcutKeyChecker>

    fun handle(context: TREEditorWindowContext)
}