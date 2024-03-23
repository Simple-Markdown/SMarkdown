package indi.midreamsheep.app.tre.model.listener.shortcut.handler.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class SaveShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
        context.fileAction.store()
    }

    @OptIn(ExperimentalComposeUiApi::class)
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