package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.window

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorWindowShortcut
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorWindowShortcut
class UndoShortcut: TREEditorShortcutKeyHandler() {
    override fun isEnable(context: TREEditorWindowContext?) = true

    /**
     * 获取快捷键的定义
     */
    override fun getKeys() = listOf(
        TREShortcutKeyStrongChecker(
            Key.Z.keyCode,
            Key.CtrlLeft.keyCode,
        ),
    )

    override fun action(context: TREEditorWindowContext?) {
        context!!.treFileManager.getStateManager().undoOperator()
    }
}