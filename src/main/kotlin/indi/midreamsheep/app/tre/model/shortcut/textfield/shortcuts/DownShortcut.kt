package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class DownShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentMarkdownLine() ?: return
        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index < markdownLineStateList.size - 1) {
            currentMarkdownLineState.line.releaseFocus()
            markdownLineStateList[index + 1].line.focus()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKey> {
        return listOf(
            TREShortcutKey(
                Key.DirectionDown.keyCode
            )
        )
    }
}