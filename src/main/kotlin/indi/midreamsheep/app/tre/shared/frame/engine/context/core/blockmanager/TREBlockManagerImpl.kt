package indi.midreamsheep.app.tre.shared.frame.engine.context.core.blockmanager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.model.editor.operator.TREInitOperator
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager

class TREBlockManagerImpl: TREBlockManager {

    private lateinit var context: TREEditorContext
    private val blockStateList = mutableStateListOf<TREBlock>()
    private var currentBlock: MutableState<TREBlock?> = mutableStateOf(null)

    private var currentTREOperator: TREOperator = TREInitOperator()

    override fun getTREBlock(index: Int) = blockStateList[index]

    override fun indexOf(treBlockState: TREBlock) = blockStateList.indexOf(treBlockState)

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

    override fun focusBlock(index: Int,typeId:Long, focus: (TREBlock) -> Unit) {
        // 获取目标block
        val treBlock = blockStateList[index]
        // 调用当前block的释放焦点方法
        getCurrentBlock()?.releaseFocus()
        // 将当前block设置为目标block
        setCurrentBlock(treBlock)
        // 对其进行执行
        focus(treBlock)
        //对所有上一级block设置为当前block
        var currentContext = context
        while (currentContext.parentContext!=null){
            if (currentContext.parentContext!!.blockManager.getCurrentBlock()==currentContext.block){
                currentContext = currentContext.parentContext!!
                continue
            }
            currentContext.parentContext!!.blockManager.focusBlock(currentContext.parentContext!!.blockManager.indexOf(currentContext.block!!),typeId)
            currentContext = currentContext.parentContext!!
        }
    }

    override fun addBlock(index: Int, blockState: TREBlock) {
        blockStateList.add(index,blockState)
        blockState.whenInsert()
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