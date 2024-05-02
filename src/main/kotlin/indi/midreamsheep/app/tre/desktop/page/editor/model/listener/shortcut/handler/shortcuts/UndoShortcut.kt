package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.shortcut.handler.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class UndoShortcut: TREEditorShortcutKeyHandler() {
    override fun isEnable(context: TREEditorContext?) = true

    /**
     * 获取快捷键的定义
     */
    override fun getKeys() = listOf(
        TREShortcutKeyStrongChecker(
            Key.Z.keyCode,
            Key.CtrlLeft.keyCode,
        ),
    )

    override fun action(context: TREEditorContext?) {
        context!!.editorFileManager.getStateManager().undoOperator()
    }
}