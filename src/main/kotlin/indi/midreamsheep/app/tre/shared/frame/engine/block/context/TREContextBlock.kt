package indi.midreamsheep.app.tre.shared.frame.engine.block.context

import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockAbstract
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

abstract class TREContextBlock(blockManager: TREBlockManager): TREBlockAbstract(blockManager) {
    fun context() = getBlockManager().getContext()
}