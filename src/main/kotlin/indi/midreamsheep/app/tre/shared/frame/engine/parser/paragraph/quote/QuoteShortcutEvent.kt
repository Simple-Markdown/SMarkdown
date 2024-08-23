package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.manager.shortcut.TREEditorShortcutEvent

class QuoteShortcutEvent: TREShortcutEvent {

    private lateinit var context: TREEditorContext
    private var standardShortcutEvent: TREEditorShortcutEvent = TREEditorShortcutEvent()

    override fun initContext(context: TREEditorContext) {
        this.context = context
        standardShortcutEvent.initContext(context)
    }

    override fun keyEvent(): Boolean {
        if (quoteKeyEvent()) return true
        return standardShortcutEvent.keyEvent()
    }

    private fun quoteKeyEvent():Boolean{

        // 处理上键的快捷键
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionUp.keyCode))){
            val currentBlock = context.blockManager.getCurrentBlock()!!
            if (context.blockManager.getCurrentBlockIndex()==0){
                if(context.parentContext!=null&&context.parentContext!!.treShortcutEvent.keyEvent()){
                    return true
                }
            }
            context.blockManager.focusBlock(context.blockManager.getCurrentBlockIndex()-1,
                TREFocusEnum.IN_TARGET_POSITION_UP,
                XPositionData(currentBlock.getComposeState().getPointerAbsoluteXPosition())
            )
            return true
        }
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.Enter.keyCode))){
            if(context.blockManager.getCurrentBlockIndex()!=context.blockManager.getSize()-1){
                return false
            }
            val currentBlock = context.blockManager.getCurrentBlock()!!
            if(currentBlock !is TRETextBlock ||currentBlock.getTextFieldValue().text.isNotEmpty()) {
                return false
            }
            // 删除当前行
            context.blockManager.removeBlock(context.blockManager.getSize()-1)
            // 如果quote块中为空，则设置删除当quote
            var targetIndex = context.parentContext!!.blockManager.getCurrentBlockIndex()+1
            if(context.blockManager.getSize()==0){
                targetIndex--
                context.parentContext!!.blockManager.removeBlock(targetIndex)
            }
            context.parentContext!!.blockManager.addBlock(targetIndex,
                TRECoreBlock(context.parentContext!!.blockManager)
            )
            // 聚焦到最新行
            context.parentContext!!.blockManager.focusBlock(targetIndex, TREFocusEnum.IN_START)
            return true
        }
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionDown.keyCode))) {
            val block = context.blockManager.getCurrentBlock()!!
            if (context.blockManager.getCurrentBlockIndex()==context.blockManager.getSize()-1
                &&context.parentContext!=null
                &&context.parentContext!!.treShortcutEvent.keyEvent()
                ){
                       return true
            }
            context.blockManager.focusBlock(
                context.blockManager.getCurrentBlockIndex()+1,
                TREFocusEnum.IN_TARGET_POSITION_DOWN,
                XPositionData(block.getComposeState().getPointerAbsoluteXPosition())
            )
            return true
        }
        return false
    }
}

