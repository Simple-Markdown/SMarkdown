package indi.midreamsheep.app.tre.shared.frame.engine.manager.block

import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager

class TREBlockState(
    val blockManager: TREBlockManager,
){
    var block: TREBlock =
        TRECoreBlock(this)
}