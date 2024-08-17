package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.manager.shortcut.TREEditorShortcutEvent

class QuoteListenerManager: TREShortcutEvent {

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
            var currentContext = context
            while (true){
                if (currentContext.blockManager.getCurrentBlockIndex()==0){
                    if(currentContext.parentContext==null) return false
                    currentContext = currentContext.parentContext!!
                    continue
                }
                break
            }
            currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()-1,
                TREFocusEnum.IN_TARGET_POSITION_UP,
                XPositionData(currentBlock.getComposeState().getPointerAbsolutePosition().first
                )
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
                    TREBlockInsert(currentContext.blockManager.getSize(), TRECoreBlock(currentContext.blockManager))
                )
                // 聚焦到新块
                currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1,
                    TREFocusEnum.STANDARD)
                return true
            }
            // 若不是最后一层，则直接聚焦到下一焦点
            if (block is TRETextBlock){
                currentContext.blockManager.focusBlock(
                    currentContext.blockManager.getCurrentBlockIndex()+1,
                    TREFocusEnum.IN_TARGET_POSITION_DOWN,
                    XPositionData(block.getComposeState().getPointerAbsolutePosition().first)
                )
                return true
            }
            currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()+1, TREFocusEnum.STANDARD)
            return true
        }
        return false
    }
}

