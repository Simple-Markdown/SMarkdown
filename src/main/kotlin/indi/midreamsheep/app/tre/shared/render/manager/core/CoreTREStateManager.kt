package indi.midreamsheep.app.tre.shared.render.manager.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.operator.TREInitOperator
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.render.block.TREBlockState
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager

class CoreTREStateManager: TREBlockManager {

    private lateinit var context: TREEditorContext
    private val markdownLineStateList = mutableStateListOf<TREBlockState>()
    private var currentMarkdownLineState: MutableState<TREBlockState?> = mutableStateOf(null)

    private var currentTREOperator: TREOperator = TREInitOperator()

    /**
     * 获取指定Block状态
     * */
    override fun getTREBlock(index: Int) = markdownLineStateList[index]

    /**
     * 获取指定Block位置
     * */
    override fun indexOf(treBlockState: TREBlockState) = markdownLineStateList.indexOf(treBlockState)

    override fun getTREBlockStateList(): SnapshotStateList<TREBlockState> {
        return markdownLineStateList
    }

    /**
     * 获取当前焦点的Block位置
     * */
    override fun getCurrentBlockIndex() = if(currentMarkdownLineState.value==null){ -1 }else{markdownLineStateList.indexOf(getCurrentBlock())}

    override fun getCurrentBlockState(): MutableState<TREBlockState?> {
        return currentMarkdownLineState
    }

    override fun getCurrentBlock(): TREBlockState? {
        return currentMarkdownLineState.value
    }

    override fun setCurrentBlockState(markdownLineState: TREBlockState?) {
        currentMarkdownLineState.value = markdownLineState
    }

    /**
     * 获取指定位置的Block的焦点
     * */
    override fun focusBlock(index: Int) = focusBlock(index) { blockState -> blockState.line.focus() }

    /**
     * 通过指定操作获取指定位置焦点
     * */
    override fun focusBlock(index: Int, focus: (TREBlockState) -> Unit) {
        val treBlockState = markdownLineStateList[index]
        getCurrentBlock()?.line?.releaseFocus()
        setCurrentBlockState(treBlockState)
        focus(treBlockState)
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