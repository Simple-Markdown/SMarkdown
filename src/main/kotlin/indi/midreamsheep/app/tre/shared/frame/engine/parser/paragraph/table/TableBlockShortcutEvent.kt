package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent

class TableBlockShortcutEvent:TREShortcutEvent {

    private lateinit var context: TREEditorContext

    override fun initContext(context: TREEditorContext) {
        this.context = context
    }

    override fun keyEvent()=quoteKeyEvent()

    private fun quoteKeyEvent():Boolean{
        val block = context.block!! as TableContextBlock
        val stateManager = context.blockManager
        // 对上键进行处理
        if(context.keyManager.match(TREShortcutKeyWeakChecker(Key.DirectionUp.keyCode))){
            if(!stateManager.getCurrentBlock()!!.getEditorShortcutState().isUpAvailable){
                return false
            }
            val upBlockIndex = block.getUpBlock()
            if (upBlockIndex!= -1){
                val xPositionData = XPositionData(stateManager.getCurrentBlock()!!.getComposeState().getPointerAbsoluteXPosition())
                stateManager.focusBlock(upBlockIndex, TREFocusEnum.IN_TARGET_POSITION_UP,xPositionData)
            }else{
                context.parentContext!!.treShortcutEvent.keyEvent()
            }
        }
        if(context.keyManager.match(TREShortcutKeyWeakChecker(Key.DirectionDown.keyCode))){
            if(!stateManager.getCurrentBlock()!!.getEditorShortcutState().isDownAvailable){
                return false
            }
            val downBlockIndex = block.getDownBlock()
            if (downBlockIndex!= -1){
                val xPositionData = XPositionData(stateManager.getCurrentBlock()!!.getComposeState().getPointerAbsoluteXPosition())
                stateManager.focusBlock(downBlockIndex, TREFocusEnum.IN_TARGET_POSITION_DOWN,xPositionData)
            }else{
                context.parentContext!!.treShortcutEvent.keyEvent()
            }
        }
        if(context.keyManager.match(TREShortcutKeyWeakChecker(Key.DirectionRight.keyCode))){
            if(!stateManager.getCurrentBlock()!!.getEditorShortcutState().isRightAvailable){
                return false
            }
            val currentBlockIndex = stateManager.getCurrentBlockIndex()
            if (currentBlockIndex!= stateManager.getSize() - 1){
                stateManager.focusBlock(currentBlockIndex + 1, TREFocusEnum.IN_START)
            }else{
                context.parentContext!!.treShortcutEvent.keyEvent()
            }
        }
        if(context.keyManager.match(TREShortcutKeyWeakChecker(Key.DirectionLeft.keyCode))){
            if(!stateManager.getCurrentBlock()!!.getEditorShortcutState().isLeftAvailable){
                return false
            }
            val currentBlockIndex = stateManager.getCurrentBlockIndex()
            if (currentBlockIndex!= 0){
                stateManager.focusBlock(currentBlockIndex - 1, TREFocusEnum.IN_END)
            }else{
                context.parentContext!!.treShortcutEvent.keyEvent()
            }
        }
        return false
    }
}