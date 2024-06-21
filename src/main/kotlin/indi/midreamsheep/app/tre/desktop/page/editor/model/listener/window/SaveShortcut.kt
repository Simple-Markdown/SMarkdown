package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.window

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorWindowShortcut
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorWindowShortcut
class SaveShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorWindowContext) {
        context.fileAction.store()
    }

    override fun isEnable(context: TREEditorWindowContext) = true

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.S.keyCode,
                Key.CtrlLeft.keyCode,
            ),
            TREShortcutKeyStrongChecker(
                Key.S.keyCode,
                Key.CtrlRight.keyCode,
            )
        )
    }
}