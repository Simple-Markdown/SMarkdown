package indi.midreamsheep.app.tre.shared.frame.engine.context.block

import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager

abstract class TREContextBlock(blockManager: TREBlockManager): TREBlockAbstract(blockManager) {
    fun context() = getBlockManager().getContext()
}