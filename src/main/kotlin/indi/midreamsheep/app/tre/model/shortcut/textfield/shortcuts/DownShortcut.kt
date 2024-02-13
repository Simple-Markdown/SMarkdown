package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler

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
    override fun getKeys(): List<TREShortcutKeyTotalMatch> {
        return listOf(
            TREShortcutKeyTotalMatch(
                Key.DirectionDown.keyCode
            )
        )
    }

    override fun isEnable(context: TREContext?): Boolean {
        if (!super.isEnable(context)) return false
        val treEditorContext = context!! as TREEditorContext
        val stateManager = treEditorContext.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentMarkdownLine() ?: return false
        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index == markdownLineStateList.size-1) return false
        val upLineState = markdownLineStateList[index + 1]
        return upLineState.line is TRETextLine &&currentMarkdownLineState.line is TRETextLine
    }
}