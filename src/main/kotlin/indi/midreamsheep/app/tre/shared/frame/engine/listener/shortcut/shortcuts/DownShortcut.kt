package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class DownShortcut: TREEditorShortcutHandler {
    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        val xPositionData = XPositionData((stateManager.getCurrentBlock() as TRETextBlock).getShortcutState().left)
        val index = stateManager.getCurrentBlockIndex()
        if(index != stateManager.getSize() - 1){
            stateManager.focusBlock(stateManager.getCurrentBlockIndex()+1,getId(),xPositionData)
        }else{
            stateManager.addTREBlockState(index+1, TRECoreBlock(stateManager))
            stateManager.focusBlock(index+1,getId())
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
        val stateManager = context.blockManager
        val currentLine = stateManager.getCurrentBlock()!!
        if(stateManager.getCurrentBlockIndex() == stateManager.getSize() - 1){
            if(currentLine is TRETextBlock){
                return currentLine.getTextFieldValue().text.isNotEmpty()&&currentLine.getShortcutState().isDownAvailable
            }
            return false
        }
        return (currentLine as TRETextBlock).getShortcutState().isDownAvailable
    }
}