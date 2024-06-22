package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class DirectionLeftShortcut: TREEditorShortcutHandler {

    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        val index = stateManager.getCurrentBlockIndex()
        stateManager.focusBlock(index-1){
            (it as TRETextBlock).focusFromLast()
        }
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.blockManager
        if(stateManager.getCurrentBlockIndex()==0){
            return false
        }
        val currentBlock = stateManager.getCurrentBlock()!!
        if (currentBlock is TRETextBlock) {
            return currentBlock.isStart()
        }
        return false
    }

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionLeft.keyCode
            )
        )
    }
}