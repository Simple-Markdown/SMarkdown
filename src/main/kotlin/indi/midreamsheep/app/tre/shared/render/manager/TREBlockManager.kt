package indi.midreamsheep.app.tre.shared.render.manager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.render.block.TREBlockState

/**
 * Block状态管理器
 * */
interface TREBlockManager {
    /**
     * 获取指定Block状态
     * */
    fun getTREBlock(index:Int): TREBlockState
    /**
     * 获取Block状态列表
     * */
    fun getTREBlockStateList(): SnapshotStateList<TREBlockState>
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
     * 获取指定位置的Block的焦点
     * */
    fun focusBlock(index:Int)
    /**
     * 通过指定操作获取指定位置焦点
     * */
    fun focusBlock(index:Int,focus: (TREBlockState)->Unit)
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