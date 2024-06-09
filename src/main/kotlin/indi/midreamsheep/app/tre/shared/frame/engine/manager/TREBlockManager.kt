package indi.midreamsheep.app.tre.shared.frame.engine.manager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TREBlockState

/**
 * Block状态管理器
 * */
interface TREBlockManager {
    /**
     * 获取指定Block状态
     * */
    fun getTREBlock(index:Int): TREBlockState
    /**
     * 获取指定Block位置
     * */
    fun indexOf(treBlockState: TREBlockState): Int
    /**
     * 获取Block状态列表
     * */
    fun getTREBlockStateList(): SnapshotStateList<TREBlockState>
    /**
     * 新增Block状态
     * */
    fun addTREBlockState(index:Int,treBlockState: TREBlockState)

    /**
     * 获取当前焦点的Block位置
     * */
    fun getCurrentBlockIndex(): Int
    /**
     * 获取当前拥有焦点的Block
     * */
    fun getCurrentBlock(): TREBlockState?
    /**
     * 获取当前Block状态
     * */
    fun getCurrentBlockState(): MutableState<TREBlockState?>
    /**
     * 设置当前Block状态
     * */
    fun setCurrentBlockState(markdownLineState: TREBlockState?)
    /**
     * 获取存储block数量
     * */
    fun getSize():Int
    /**
     * 获取指定位置的Block的焦点
     * */
    fun focusBlock(index:Int) = focusBlock(index){it.block.focus()}
    /**
     * 通过指定操作获取指定位置焦点
     * */
    fun focusBlock(index:Int,focus: (TREBlockState)->Unit)
    /**
     * 新增block块，会触发block的回调操作
     * */
    fun addBlock(block: TREBlockState) = addBlock(getSize()-1,block)
    /**
     * 在指定位置新增block
     * */
    fun addBlock(index:Int,block: TREBlockState)

    /**
     * 获取编辑器上下文
     * */
    fun getContext(): TREEditorContext
    /**
     * 设置编辑器上下文
     * */
    fun setContext(context: TREEditorContext)
    /**
     * 提交命令
     * */
    fun executeOperator(operation: TREOperator)
    /**
     * 撤销上一次命令
     * */
    fun undoOperator()
    /**
     * 执行下一个命令
     * */
    fun doOperator()
}