package indi.midreamsheep.app.tre.shared.frame.manager

import indi.midreamsheep.app.tre.shared.frame.TREEditorContext

/**
 * 上下文快捷键的处理器，用于处理未能在下两级处理器处理的快捷键事件
 * */
interface TREShortcutEvent{
    /**
     * 初始化设置上下文
     * */
    fun initContext(context: TREEditorContext)
    /**
     * 处理快捷键事件
     * */
    fun keyEvent(): Boolean
}