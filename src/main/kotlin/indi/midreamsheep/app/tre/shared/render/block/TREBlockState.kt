package indi.midreamsheep.app.tre.shared.render.block

import indi.midreamsheep.app.tre.model.editor.block.TREBlock
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager

class TREBlockState(
    val markdownLineInter: TREBlockManager,
){
    var line: TREBlock =
        TRECoreBlock(this)
}