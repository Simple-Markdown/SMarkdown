package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class DirectionRightShortcut: TREEditorShortcutHandler {

    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        val index = stateManager.getCurrentBlockIndex()
        stateManager.focusBlock(index+1){
            (it as TRETextBlock).focusFromStart()
        }
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.blockManager
        if(stateManager.getCurrentBlockIndex()==stateManager.getSize()-1){
            return false
        }
        val currentBlock = stateManager.getCurrentBlock()!!
        if (currentBlock is TRETextBlock) {
            return currentBlock.isEnd()
        }
        return false
    }

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionRight.keyCode
            )
        )
    }
}