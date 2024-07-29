package indi.midreamsheep.app.tre.shared.frame.manager.blockmanager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.model.editor.operator.TREInitOperator
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlock
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

class TREBlockManagerImpl: TREBlockManager {

    private lateinit var context: TREEditorContext
    private val blockStateList = mutableStateListOf<TREBlock>()
    private var currentBlock: MutableState<TREBlock?> = mutableStateOf(null)

    private var currentTREOperator: TREOperator = TREInitOperator()

    override fun getTREBlock(index: Int) = blockStateList[index]

    override fun indexOf(treBlock: TREBlock) = blockStateList.indexOf(treBlock)

    override fun getTREBlockList(): SnapshotStateList<TREBlock> {
        return blockStateList
    }

    override fun addTREBlockState(index:Int,treBlockState: TREBlock) = blockStateList.add(index,treBlockState)

    override fun getCurrentBlockIndex() = if(currentBlock.value==null){ -1 }else{blockStateList.indexOf(getCurrentBlock())}

    override fun getCurrentBlockState(): MutableState<TREBlock?> {
        return currentBlock
    }

    override fun getCurrentBlock(): TREBlock? {
        return currentBlock.value
    }

    override fun setCurrentBlock(markdownLineState: TREBlock?) {
        currentBlock.value = markdownLineState
    }

    override fun getNextBlock(treBlock: TREBlock): TREBlock? {
        val index = indexOf(treBlock)
        if (index<getSize()) return getTREBlock(index+1)
        if (context.parentContext==null) return null
        val parentBlockManager = context.parentContext!!.blockManager
        return parentBlockManager.getNextBlock(parentBlockManager.getCurrentBlock()!!)
    }

    override fun getPreviousBlock(treBlock: TREBlock): TREBlock? {
        val index = indexOf(treBlock)
        if (index>0) return getTREBlock(index-1)
        if (context.parentContext==null) return null
        val parentBlockManager = context.parentContext!!.blockManager
        return parentBlockManager.getPreviousBlock(parentBlockManager.getCurrentBlock()!!)
    }

    override fun getSize() = blockStateList.size

    override fun focusBlock(index: Int,focus: (TREBlock) -> Unit) {
        // 释放焦点
        releaseCurrentBlock()
        // 获取焦点
        focusBlock(blockStateList[index],focus)
    }

    private fun releaseCurrentBlock(){
        if(currentBlock.value == null) return
        var contextPointer: TREEditorContext? = context
        while (contextPointer!=null) {
            contextPointer.blockManager.getCurrentBlock()?.releaseFocus()
            contextPointer.blockManager.setCurrentBlock(null)
            contextPointer = contextPointer.parentContext
        }
    }

    private fun focusBlock(treBlock: TREBlock, focus: (TREBlock) -> Unit){
        //对所有上一级block设置为当前block
        var currentContext = context
        while (currentContext.parentContext!=null){
            if (currentContext.parentContext!!.blockManager.getCurrentBlock()==currentContext.block){
                currentContext = currentContext.parentContext!!
                continue
            }
            currentContext.parentContext!!.blockManager.setCurrentBlock(currentContext.block)
            currentContext = currentContext.parentContext!!
        }
        // 将当前block设置为目标block
        setCurrentBlock(treBlock)
        // 对其进行执行
        focus(treBlock)
    }

    override fun addBlock(index: Int, block: TREBlock) {
        blockStateList.add(index,block)
        block.setBlockManager(this)
        block.getBlockManager().setContext(getContext())
        block.whenInsert()
    }

    override fun removeBlock(index: Int) {
        val removeAt = blockStateList.removeAt(index)
        removeAt.whenRemove()
    }

    override fun getContext() = context
    override fun setContext(context: TREEditorContext) {
        this.context = context
    }

    override fun executeOperator(operation: TREOperator) {
        //执行操作
        currentTREOperator.nextOperator = operation
        //1.将操作添加到操作链表中
        operation.preOperator = currentTREOperator
        operation.operatorIndex = currentTREOperator.operatorIndex + 1
        doOperator()
    }

    override fun undoOperator() {
        if (currentTREOperator.preOperator == null) {
            return
        }
        currentTREOperator.undo(this)
        currentTREOperator = currentTREOperator.preOperator!!
    }

    override fun doOperator() {
        if (currentTREOperator.nextOperator == null) {
            return
        }
        currentTREOperator = currentTREOperator.nextOperator!!
        currentTREOperator.execute(this)
    }
}