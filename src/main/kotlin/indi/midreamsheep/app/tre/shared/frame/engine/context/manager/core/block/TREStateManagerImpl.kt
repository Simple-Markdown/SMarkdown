package indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.block

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.editor.operator.TREInitOperator
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TREBlock

class TREStateManagerImpl: TREBlockManager {

    private lateinit var context: TREEditorWindowContext
    private val blockStateList = mutableStateListOf<TREBlock>()
    private var currentMarkdownLineState: MutableState<TREBlock?> = mutableStateOf(null)

    private var currentTREOperator: TREOperator = TREInitOperator()

    override fun getTREBlock(index: Int) = blockStateList[index]

    override fun indexOf(treBlockState: TREBlock) = blockStateList.indexOf(treBlockState)

    override fun getTREBlockList(): SnapshotStateList<TREBlock> {
        return blockStateList
    }

    override fun addTREBlockState(index:Int,treBlockState: TREBlock) = blockStateList.add(index,treBlockState)

    override fun getCurrentBlockIndex() = if(currentMarkdownLineState.value==null){ -1 }else{blockStateList.indexOf(getCurrentBlock())}

    override fun getCurrentBlockState(): MutableState<TREBlock?> {
        return currentMarkdownLineState
    }

    override fun getCurrentBlock(): TREBlock? {
        return currentMarkdownLineState.value
    }

    override fun setCurrentBlockState(markdownLineState: TREBlock?) {
        currentMarkdownLineState.value = markdownLineState
    }

    override fun getSize() = blockStateList.size

    override fun focusBlock(index: Int, focus: (TREBlock) -> Unit) {
        val treBlockState = blockStateList[index]
        getCurrentBlock()?.releaseFocus()
        setCurrentBlockState(treBlockState)
        focus(treBlockState)
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
    override fun setContext(context: TREEditorWindowContext) {
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