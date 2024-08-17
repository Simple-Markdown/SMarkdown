package indi.midreamsheep.app.tre.shared.frame.engine.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.shortcut.TRELineShortcutHandler

@EditorShortcut
class DownShortcut: TRELineShortcutHandler {
    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        val xPositionData = XPositionData(stateManager.getCurrentBlock()!!.getComposeState().getPointerAbsolutePosition().first)
        val index = stateManager.getCurrentBlockIndex()
        if(index != stateManager.getSize() - 1){
            stateManager.focusBlock(stateManager.getCurrentBlockIndex()+1,
                TREFocusEnum.IN_TARGET_POSITION_DOWN,xPositionData)
        }else{
            stateManager.addTREBlockState(index+1, TRECoreBlock(stateManager))
            stateManager.focusBlock(index+1, TREFocusEnum.STANDARD)
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
                return currentLine.getTextFieldValue().text.isNotEmpty()&&currentLine.getEditorShortcutState().isDownAvailable
            }
            return false
        }
        return (currentLine as TRETextBlock).getEditorShortcutState().isDownAvailable
    }
}