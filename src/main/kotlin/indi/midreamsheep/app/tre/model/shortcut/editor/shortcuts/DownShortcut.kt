package indi.midreamsheep.app.tre.model.shortcut.editor.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.api.annotation.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKey

@EditorShortcutKey
class DownShortcut: TREEditorShortcutKey {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentMarkdownLineState() ?: return
        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index < markdownLineStateList.size - 1) {
            currentMarkdownLineState.line.releaseFocus()
            markdownLineStateList[index + 1].line.focus()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<List<Long>> {
        return listOf(
            listOf(
                Key.DirectionDown.keyCode
            )
        )
    }
}