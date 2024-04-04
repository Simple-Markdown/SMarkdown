package indi.midreamsheep.app.tre.model.listener.shortcut.textfield.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class DownShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentBlock() ?: return
        val markdownLineStateList = stateManager.getTREBlockStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index < markdownLineStateList.size - 1) {
            currentMarkdownLineState.line.releaseFocus()
            markdownLineStateList[index + 1].line.focus()
        }
    }

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionDown.keyCode
            )
        )
    }

    override fun isEnable(context: TREContext?): Boolean {
        if (!super.isEnable(context)) return false
        val treEditorContext = context!! as TREEditorContext
        val stateManager = treEditorContext.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentBlock() ?: return false
        val markdownLineStateList = stateManager.getTREBlockStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index == markdownLineStateList.size-1) return false
        val upLineState = markdownLineStateList[index + 1]
        return upLineState.line is TRETextBlock &&currentMarkdownLineState.line is TRETextBlock
    }
}