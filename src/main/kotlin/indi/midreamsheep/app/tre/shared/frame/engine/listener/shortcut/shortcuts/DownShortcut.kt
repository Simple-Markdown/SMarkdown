package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class DownShortcut: TREEditorShortcutHandler {
    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
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

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.blockManager
        if(stateManager.getCurrentBlockIndex() == stateManager.getSize() - 1){
            val currentLine = stateManager.getCurrentBlock()!!
            if(currentLine is TRETextBlock){
                return currentLine.getTextFieldValue().text.isNotEmpty()
            }
        }
        return true
    }
}