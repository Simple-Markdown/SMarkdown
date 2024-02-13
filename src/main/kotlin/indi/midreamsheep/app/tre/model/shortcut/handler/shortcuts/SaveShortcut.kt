package indi.midreamsheep.app.tre.model.shortcut.handler.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class SaveShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
        context.fileAction.store()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyTotalMatch> {
        return listOf(
            TREShortcutKeyTotalMatch(
                Key.S.keyCode,
                Key.CtrlLeft.keyCode,
            ),
            TREShortcutKeyTotalMatch(
                Key.S.keyCode,
                Key.CtrlRight.keyCode,
            )
        )
    }
}