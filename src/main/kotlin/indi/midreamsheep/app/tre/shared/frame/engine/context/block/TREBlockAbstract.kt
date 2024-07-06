package indi.midreamsheep.app.tre.shared.frame.engine.context.block;

import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager

abstract class TREBlockAbstract(private var blockManager: TREBlockManager) : TREBlock {
    override fun getBlockManager() = blockManager
    override fun setBlockManager(blockManager: TREBlockManager) {this.blockManager = blockManager}
}