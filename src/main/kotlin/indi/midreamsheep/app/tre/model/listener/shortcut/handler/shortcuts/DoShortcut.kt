package indi.midreamsheep.app.tre.model.listener.shortcut.handler.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class DoShortcut: TREEditorShortcutKeyHandler() {
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

    override fun action(context: TREEditorContext?) {
        context!!.editorFileManager.getStateManager().doOperator()
    }
}