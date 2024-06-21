package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock

@TextFieldShortcutKey
class DownShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorWindowContext) {
        val stateManager = context.treFileManager.getStateManager()
        val index = stateManager.getCurrentBlockIndex()
        if(index != stateManager.getSize() - 1){
            stateManager.focusBlock(stateManager.getCurrentBlockIndex()+1)
            return
        }
        stateManager.addTREBlockState(index+1, TRECoreBlock(stateManager))
        stateManager.focusBlock(index+1)
    }

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionDown.keyCode
            )
        )
    }

    override fun isEnable(context: TREEditorWindowContext): Boolean {
        val stateManager = context.treFileManager.getStateManager()
        if(stateManager.getCurrentBlockIndex() == stateManager.getSize() - 1){
            val currentLine = stateManager.getCurrentBlock()!!
            if(currentLine is TRETextBlock){
                return currentLine.getTextFieldValue().text.isNotEmpty()
            }
        }
        return true
    }
}