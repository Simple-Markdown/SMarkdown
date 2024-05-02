package indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
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

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.editorFileManager.getStateManager()
        //TODO 自动换行实现
        return stateManager.getCurrentBlockIndex() != stateManager.getTREBlockStateList().size - 1
    }
}