package indi.midreamsheep.app.markdown.model.shortcut.editor.shortcuts

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.markdown.context.api.annotation.EditorShortcutKey
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.shortcut.editor.TREEditorShortcutKey

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