package indi.midreamsheep.app.tre.shared.frame.engine.manager.block;

import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.api.tre.TREId
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton

interface TREBlock:TREId {
    /**
     * 从上次获取焦点的位置获取焦点
     * */
    fun focus()
    fun focusFromLast()
    fun focusFormStart()
    /**
     * 释放焦点
     * */
    fun releaseFocus();
    /**
     * 获取当前的composable
     * */
    fun getDisplay():Display
    /**
     * 获取前置按钮
     */
    fun getPreButton(): TRELinePreButton

    /**
     * 获取当前的内容
     * */
    fun getContent():String
    fun getBlockManager(): TREBlockManager

    /**
     * 当block被加入manager时调用
     * */
    fun whenInsert()
    /**
     * 当block被移除manager时调用
     * */
    fun whenRemove()
}