package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class UpShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentMarkdownLine() ?: return
        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        currentMarkdownLineState.line.releaseFocus()
        markdownLineStateList[markdownLineStateList.indexOf(currentMarkdownLineState) - 1].line.focus()
    }

    override fun getKeys(): List<TREShortcutKeyTotalMatch> {
        return listOf(
            TREShortcutKeyTotalMatch(
                Key.DirectionUp.keyCode
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
        if (index == 0) return false
        val upLineState = markdownLineStateList[index - 1]
        return upLineState.line is TRETextLine&&currentMarkdownLineState.line is TRETextLine
    }
}