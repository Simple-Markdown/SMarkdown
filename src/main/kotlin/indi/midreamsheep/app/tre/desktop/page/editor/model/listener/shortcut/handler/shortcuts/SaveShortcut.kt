package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.shortcut.handler.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class SaveShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
        context.fileAction.store()
    }

    override fun isEnable(context: TREEditorContext) = true

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