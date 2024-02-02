package indi.midreamsheep.app.markdown.model.shortcut.editor.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.markdown.context.api.annotation.EditorShortcutKey
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.shortcut.editor.TREEditorShortcutKey

@EditorShortcutKey
class UpShortcut: TREEditorShortcutKey {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentMarkdownLineState() ?: return
        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index > 0) {
            currentMarkdownLineState.line.releaseFocus()
            markdownLineStateList[index - 1].line.focus()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<List<Long>> {
        return listOf(
            listOf(
                Key.DirectionUp.keyCode
            )
        )
    }
}