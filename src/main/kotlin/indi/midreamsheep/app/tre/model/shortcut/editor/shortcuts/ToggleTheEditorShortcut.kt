package indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyHandler

@EditorShortcutKey
class ToggleTheEditorShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
       context.editorStateAction.toggleMode()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKey> {
        return listOf(
            TREShortcutKey(Key.CtrlLeft.keyCode,Key.Slash.keyCode)
        )
    }
}