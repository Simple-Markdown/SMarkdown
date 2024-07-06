package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.OffsetCustomData
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREShortcutEvent

class ListShortcutEvent: TREShortcutEvent() {
    override fun keyEvent(): Boolean {
        if (listKeyEvent()) return true
        return super.keyEvent()
    }

    private fun listKeyEvent():Boolean{
        val listBlock = context.block!! as ListBlock
        // 处理上键的快捷键
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionUp.keyCode))){
            var currentBlock = context.blockManager.getCurrentBlock()!!
            var currentContext = context
            while (true){
                if (currentContext.blockManager.getCurrentBlockIndex()==0){
                    if(currentContext.parentContext==null) return false
                    currentContext = currentContext.parentContext!!
                    currentBlock = currentContext.blockManager.getCurrentBlock()!!
                    continue
                }
                break
            }
            if (currentBlock is TRETextBlock){
                currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()-1,
                    XPositionData(currentBlock.getShortcutState().left)
                )
                return true
            }
            currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()-1)
            return true
        }
        // 处理下键
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionDown.keyCode))) {
            var currentBlock = context.blockManager.getCurrentBlock()!!
            var currentContext = context
            while (true){
                if (currentContext.blockManager.getCurrentBlockIndex()==currentContext.blockManager.getSize()-1){
                    if(currentContext.parentContext==null) break
                    currentContext = currentContext.parentContext!!
                    currentBlock = currentContext.blockManager.getCurrentBlock()!!
                    continue
                }
                break
            }
            if (currentContext.blockManager.getCurrentBlockIndex()==currentContext.blockManager.getSize()-1){
                currentContext.blockManager.executeOperator(
                    TREBlockInsert(currentContext.blockManager.getSize(), TRECoreBlock(currentBlock.getBlockManager()))
                )
                currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1)
                return true
            }
            if (currentBlock is TRETextBlock){
                currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1,
                    XPositionData(currentBlock.getShortcutState().left)
                )
                return true
            }
            currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1)
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
                val previousBlock = parentBlockManager.getPreviousBlock(context.blockManager.getCurrentBlock()!!)
                var startIndex = parentBlockManager.getCurrentBlockIndex()
                parentBlockManager.removeBlock(startIndex)
                if (previousBlock !is ListBlock){
                    //则将当前blockManager的所有block放入父级
                    for (treBlock in context.blockManager.getTREBlockList()) {
                        parentBlockManager.addBlock(startIndex, treBlock)
                        treBlock.setBlockManager(parentBlockManager)
                        startIndex++
                    }
                    //设置焦点到第一行
                    parentBlockManager.focusBlock(startIndex-context.blockManager.getSize(),OffsetCustomData(0))
                    return true
                }
                //将当前块的内容送进上一级块中
                val blockManager = previousBlock.getBlockManager()
                for (treBlock in context.blockManager.getTREBlockList()) {
                    blockManager.addBlock(blockManager.getSize(), treBlock)
                    treBlock.setBlockManager(blockManager)
                }
                blockManager.focusBlock(blockManager.getSize()-context.blockManager.getSize(),OffsetCustomData(0))
                return true
            }
        }
        return false
    }
}