package indi.midreamsheep.app.tre.shared.frame.engine.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.shortcut.TRELineShortcutHandler

@EditorShortcut
class DirectionLeftShortcut: TRELineShortcutHandler {

    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        stateManager.focusBlock(stateManager.getCurrentBlockIndex()-1, TREFocusEnum.IN_END)
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.blockManager
        if(stateManager.getCurrentBlockIndex()==0){
            return false
        }
        val currentBlock = stateManager.getCurrentBlock()!!
        if (currentBlock is TRETextBlock) {
            return currentBlock.getShortcutState().isLeftAvailable
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