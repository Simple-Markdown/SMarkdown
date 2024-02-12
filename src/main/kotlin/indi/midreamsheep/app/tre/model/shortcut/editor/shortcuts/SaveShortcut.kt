package indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyHandler

@EditorShortcutKey
class SaveShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
        context.fileAction.store()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKey> {
        return listOf(
            TREShortcutKey(
                Key.S.keyCode,
                Key.CtrlLeft.keyCode,
            ),
            TREShortcutKey(
                Key.S.keyCode,
                Key.CtrlRight.keyCode,
            )
        )
    }
}