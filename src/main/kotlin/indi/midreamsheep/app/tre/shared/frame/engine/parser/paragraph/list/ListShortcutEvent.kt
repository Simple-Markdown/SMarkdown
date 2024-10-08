package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.context.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.manager.shortcut.TREEditorShortcutEvent

class ListShortcutEvent: TREShortcutEvent {

    private lateinit var context: TREEditorContext
    private var standardShortcutEvent:TREEditorShortcutEvent = TREEditorShortcutEvent()

    override fun initContext(context: TREEditorContext) {
        this.context = context
        standardShortcutEvent.initContext(context)
    }

    override fun keyEvent(): Boolean {
        if (listKeyEvent()) return true
        return standardShortcutEvent.keyEvent()
    }

    private fun listKeyEvent():Boolean{
        val listBlock = context.block!! as ListBlock
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
        // 处理下键
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionDown.keyCode))) {
            val currentBlock = context.blockManager.getCurrentBlock()!!
            if (context.blockManager.getCurrentBlockIndex()==context.blockManager.getSize()-1){
                if(context.parentContext!=null&&context.parentContext!!.treShortcutEvent.keyEvent()){
                    return true
                }
            }
            context.blockManager.focusBlock(context.blockManager.getCurrentBlockIndex()+1,
                TREFocusEnum.IN_TARGET_POSITION_UP,
                XPositionData(currentBlock.getComposeState().getPointerAbsoluteXPosition())
            )
            return true
        }
        // 处理enter键
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.Backspace.keyCode))){
            //判断光标是否在最前面
            var isStart = false
            var currentBlock = context.blockManager.getCurrentBlock()!!
            var currentContext = context
            while (true){
                if (currentContext.blockManager.getCurrentBlockIndex()!=0){
                    break
                }
                if (currentBlock is TREContextBlock){
                    currentContext = currentContext.parentContext!!
                    currentBlock = currentContext.blockManager.getCurrentBlock()!!
                }
                if (currentBlock is TRETextBlock){
                    isStart = currentBlock.isStart()
                    break
                }
            }
            if (isStart){
                // 若listBlock的前缀显示,则判断上一个块的状态
                val parentBlockManager = context.parentContext!!.blockManager
                val previousBlock = parentBlockManager.getPreviousBlock(parentBlockManager.getCurrentBlock()!!)
                var startIndex = parentBlockManager.getCurrentBlockIndex()
                parentBlockManager.removeBlock(startIndex)
                if (previousBlock !is ListBlock){
                    //则将当前blockManager的所有block放入父级
                    for (treBlock in context.blockManager.getTREBlockList()) {
                        parentBlockManager.addBlock(startIndex, treBlock)
                        startIndex++
                    }
                    //设置焦点到第一行
                    parentBlockManager.focusBlock(startIndex-context.blockManager.getSize(), TREFocusEnum.IN_END)
                    return true
                }
                //将当前块的内容送进上一级块中
                val blockManager = previousBlock.innerContext.blockManager
                for (treBlock in context.blockManager.getTREBlockList()) {
                    blockManager.addBlock(blockManager.getSize(), treBlock)
                }
                blockManager.focusBlock(blockManager.getSize()-context.blockManager.getSize(), TREFocusEnum.IN_END)
                return true
            }
        }
        // 处理Enter键
        if(context.keyManager.match(TREShortcutKeyStrongChecker(Key.Enter.keyCode))) {
            if(context.blockManager.getCurrentBlockIndex()!=0){
                return false
            }
            val currentBlock = context.blockManager.getCurrentBlock()
            if(currentBlock !is TRETextBlock){
                return false
            }
            val textFieldValue = currentBlock.getTextFieldValue()
            if (textFieldValue.text.isEmpty()){
                //将当前行用Core块替代
                val treCoreBlock = TRECoreBlock(context.blockManager)
                val currentBlockIndex = context.parentContext!!.blockManager.getCurrentBlockIndex()
                context.parentContext!!.blockManager.executeOperator(
                    TREOperatorGroup().apply {
                        addOperator(TREBlockDelete(currentBlockIndex))
                        addOperator(TREBlockInsert(currentBlockIndex,treCoreBlock))
                    }
                )
                context.parentContext!!.blockManager.focusBlock(currentBlockIndex, TREFocusEnum.STANDARD)
                return true
            }
            // 对文本进行切割
            val str1 = textFieldValue.text.substring(0,textFieldValue.selection.start)
            val str2 = textFieldValue.text.substring(textFieldValue.selection.start)
            val newBlock = listBlock.listType.build(str2, context.parentContext!!)
            context.parentContext!!.blockManager.executeOperator(
                TREOperatorGroup().apply {
                    addOperator(TREBlockInsert(context.parentContext!!.blockManager.getCurrentBlockIndex()+1, newBlock))
                }
            )
            // 设置原来的block
            currentBlock.setTextFieldValue(TextFieldValue(str1, TextRange(0)))
            // 聚焦到下一个block
            context.parentContext!!.blockManager.focusBlock(context.parentContext!!.blockManager.getCurrentBlockIndex()+1,
                TREFocusEnum.IN_START)
            return true
        }
        return false
    }
}