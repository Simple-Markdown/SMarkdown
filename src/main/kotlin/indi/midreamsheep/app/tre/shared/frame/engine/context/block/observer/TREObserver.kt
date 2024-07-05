package indi.midreamsheep.app.tre.shared.frame.engine.context.block.observer

import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager

/**
 * 监听特定的styleTextTree
 * */
interface TREObserver {
    /**
     * 通知行更新
     * */
    fun update(blockIndex:Int,blockManager: TREBlockManager)
}