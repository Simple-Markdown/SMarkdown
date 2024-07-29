package indi.midreamsheep.app.tre.shared.frame.engine.block;

import indi.midreamsheep.app.tre.shared.api.tre.TREObjectId
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

/**
 * tre编辑器块的基本接口
 * */
interface TREBlock:TREObjectId {
    /**
     * 从上次获取焦点的位置获取焦点
     * @param typeId 用于标记调用者
     * @param data 用于特殊形式的数据传递
     * */
    fun focus(typeId:Long,data: CustomData = CustomData.NONE)
    /**
     * 释放焦点
     * */
    fun releaseFocus()
    /**
     * 获取用于显示实体类
     * */
    fun getTREBlockDisplay(): TREBlockDisplay

    /**
     * 获取当前的内容
     * */
    fun getContent():String
    fun getBlockManager(): TREBlockManager
    fun setBlockManager(blockManager: TREBlockManager)
    /**
     * 当block被加入manager时调用
     * */
    fun whenInsert()
    /**
     * 当block被移除manager时调用
     * */
    fun whenRemove()
}