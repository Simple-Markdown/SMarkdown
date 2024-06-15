package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.shortcut.handler.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class DoShortcut: TREEditorShortcutKeyHandler() {
    override fun isEnable(context: TREEditorWindowContext?): Boolean {
        return true
    }

    /**
     * 获取快捷键的定义
     */
    override fun getKeys() = listOf(
        TREShortcutKeyStrongChecker(
            Key.CtrlLeft.keyCode,
            Key.Z.keyCode,
            Key.ShiftLeft.keyCode
        )
    )

    override fun action(context: TREEditorWindowContext) {
        context.editorFileManager.getStateManager().doOperator()
    }
}