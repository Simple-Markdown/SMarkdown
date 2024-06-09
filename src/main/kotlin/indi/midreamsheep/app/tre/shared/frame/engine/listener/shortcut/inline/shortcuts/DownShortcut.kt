package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TREBlockState
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRETextBlock

@TextFieldShortcutKey
class DownShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()
        val index = stateManager.getCurrentBlockIndex()
        if(index != stateManager.getTREBlockStateList().size - 1){
            stateManager.focusBlock(stateManager.getCurrentBlockIndex()+1)
            return
        }
        stateManager.addTREBlockState(index+1, TREBlockState(stateManager))
        stateManager.focusBlock(index+1)
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
        if(stateManager.getCurrentBlockIndex() == stateManager.getTREBlockStateList().size - 1){
            val currentLine = stateManager.getCurrentBlock()!!.block
            if(currentLine is TRETextBlock){
                return currentLine.getTextFieldValue().text.isNotEmpty()
            }
        }
        return true
    }
}