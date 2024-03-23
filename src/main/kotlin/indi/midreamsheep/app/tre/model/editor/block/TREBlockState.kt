package indi.midreamsheep.app.tre.model.editor.block

import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager

class TREBlockState(
    val markdownLineInter: TREStateManager,
){
    var line: TREBlock =
        TRECoreBlock(this)
}