package indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.api.annotation.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKey

@EditorShortcutKey
class ToggleTheEditorShortcut: TREEditorShortcutKey {

    override fun action(context: TREEditorContext) {
       context.isSourceMode.value = true
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<List<Long>> {
        return listOf(
            listOf(Key.CtrlLeft.keyCode,Key.Slash.keyCode)
        )
    }
}