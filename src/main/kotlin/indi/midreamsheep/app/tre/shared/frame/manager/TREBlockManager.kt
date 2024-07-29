package indi.midreamsheep.app.tre.shared.frame.manager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.CustomData
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum

/**
 * Block管理器，用于管理当前上下文下的块
 * */
interface TREBlockManager {
    /**
     * 获取指定Block状态
     * */
    fun getTREBlock(index:Int): TREBlock
    /**
     * 获取指定Block位置
     * */
    fun indexOf(treBlock: TREBlock): Int
    /**
     * 获取Block状态列表
     * */
    fun getTREBlockList(): SnapshotStateList<TREBlock>
    /**
     * 新增Block状态
     * */
    fun addTREBlockState(index:Int,treBlockState: TREBlock)

    /**
     * 获取当前焦点的Block位置
     * */
    fun getCurrentBlockIndex(): Int
    /**
     * 获取当前拥有焦点的Block
     * */
    fun getCurrentBlock(): TREBlock?
    /**
     * 获取当前Block状态
     * */
    fun getCurrentBlockState(): MutableState<TREBlock?>
    /**
     * 设置当前Block状态
     * */
    fun setCurrentBlock(markdownLineState: TREBlock?)
    /**
     * 获取指定块的下一个块
     * */
    fun getNextBlock(treBlock: TREBlock): TREBlock?
    /**
     * 获取指定块的上一个块
     * */
    fun getPreviousBlock(treBlock: TREBlock): TREBlock?
    /**
     * 获取存储block数量
     * */
    fun getSize():Int
    /**
     * 通过指定操作获取指定位置焦点
     * */
    fun focusBlock(index:Int,focus: (TREBlock)->Unit)
    /**
     * 标准获取焦点
     * */
    fun focusBlock(index:Int) = focusBlock(index,TREFocusEnum.STANDARD)
    /**
     * 获取指定位置的Block的焦点
     * */
    fun focusBlock(index: Int, data: CustomData = CustomData.NONE, typeId: Long = data.getId()) = focusBlock(index){ it.focus(typeId,data) }
    /**
     * 获取指定位置的Block的焦点
     * */
    fun focusBlock(index:Int, type: TREFocusEnum, data: CustomData = CustomData.NONE) = focusBlock(index){ it.focus(type.id,data) }
    /**
     * 新增block块，会触发block的回调操作
     * */
    fun addBlock(blockState: TREBlock) = addBlock(getSize(),blockState)
    /**
     * 在指定位置新增block
     * */
    fun addBlock(index:Int, block: TREBlock)
    /**
     * 删除指定block
     * */
    fun removeBlock(index:Int)

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