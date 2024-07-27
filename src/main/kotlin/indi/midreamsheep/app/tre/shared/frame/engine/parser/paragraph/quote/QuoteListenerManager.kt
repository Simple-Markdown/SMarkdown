package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DownShortcut
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool

class QuoteListenerManager: TREShortcutEvent() {

    override fun keyEvent(): Boolean {
        if (quoteKeyEvent()) return true
        return super.keyEvent()
    }

    private fun quoteKeyEvent():Boolean{
        // 处理上键的快捷键
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionUp.keyCode))){
            val currentBlock = context.blockManager.getCurrentBlock()!!
            var currentContext = context
            while (true){
                if (currentContext.blockManager.getCurrentBlockIndex()==0){
                    if(currentContext.parentContext==null) return false
                    currentContext = currentContext.parentContext!!
                    continue
                }
                break
            }
            if (currentBlock is TRETextBlock){
                currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()-1,XPositionData(currentBlock.getShortcutState().left))
                return true
            }
            currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()-1)
            return true
        }
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.Enter.keyCode))){
            if(context.blockManager.getCurrentBlockIndex()!=context.blockManager.getSize()-1){
                return false
            }
            val currentBlock = context.blockManager.getCurrentBlock()!!
            if(currentBlock !is TRETextBlock||currentBlock.getTextFieldValue().text.isNotEmpty()) {
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
            context.parentContext!!.blockManager.addBlock(targetIndex,TRECoreBlock(context.parentContext!!.blockManager))
            // 聚焦到最新行
            context.parentContext!!.blockManager.focusBlock(targetIndex)
            return true
        }
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionDown.keyCode))) {
            // 获取当前的block块
            val block = context.blockManager.getCurrentBlock()!!
            // 定义一个可变的currentContext，找到上一级能够向下进行传递焦点的上下文
            var currentContext = context
            while (true){
                //如果处于最后一个块即不能传递焦点就继续循环
                if (currentContext.blockManager.getCurrentBlockIndex()==currentContext.blockManager.getSize()-1){
                    // 若已经递归到最上层则
                    if(currentContext.parentContext==null) break
                    // 设置为上一级上下文
                    currentContext = currentContext.parentContext!!
                    continue
                }
                break
            }
            // 若是最后一层，则向下新增一个块
            if (currentContext.blockManager.getCurrentBlockIndex()==currentContext.blockManager.getSize()-1){
                // 提交命令
                currentContext.blockManager.executeOperator(
                    TREBlockInsert(currentContext.blockManager.getSize(),TRECoreBlock(currentContext.blockManager))
                )
                // 聚焦到新块
                currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1)
                return true
            }
            // 若不是最后一层，则直接聚焦到下一焦点
            if (block is TRETextBlock){
                currentContext.blockManager.focusBlock(
                    currentContext.blockManager.getCurrentBlockIndex()+1,
                    getIdFromPool(DownShortcut::class.java),
                    XPositionData(block.getShortcutState().left))
                return true
            }
            currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1)
            return true
        }
        return false
    }
}

