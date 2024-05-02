package indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.render.block.TRETextBlock

@TextFieldShortcutKey
class UpShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentBlock() ?: return
        val markdownLineStateList = stateManager.getTREBlockStateList()
        currentMarkdownLineState.line.releaseFocus()
        markdownLineStateList[markdownLineStateList.indexOf(currentMarkdownLineState) - 1].line.focus()
    }

    override fun getKeys(): List<TREShortcutKeyWeakChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.DirectionUp.keyCode
            )
        )
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val treEditorContext = context!! as TREEditorContext
        val stateManager = treEditorContext.editorFileManager.getStateManager()
        val currentMarkdownLineState = stateManager.getCurrentBlock() ?: return false
        val markdownLineStateList = stateManager.getTREBlockStateList()
        val index = markdownLineStateList.indexOf(currentMarkdownLineState)
        if (index == 0) return false
        val upLineState = markdownLineStateList[index - 1]
        return upLineState.line is TRETextBlock &&currentMarkdownLineState.line is TRETextBlock
    }
}