package indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import indi.midreamsheep.app.tre.context.api.annotation.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@EditorShortcutKey
class SaveShortcut: TREEditorShortcutKey {


    @OptIn(DelicateCoroutinesApi::class)
    override fun action(context: TREEditorContext) {
        GlobalScope.launch {
            context.stateString.value = "Saving"
            context.editorFileManager.store()
            context.stateString.value = "saving success"
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<List<Long>> {
        return listOf(
            listOf(
                Key.S.keyCode,
                Key.CtrlLeft.keyCode,
            ),
            listOf(
                Key.S.keyCode,
                Key.CtrlRight.keyCode,
            )
        )
    }
}