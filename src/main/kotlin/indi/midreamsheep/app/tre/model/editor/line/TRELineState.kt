package indi.midreamsheep.app.tre.model.editor.line

import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager

class TRELineState(
    val markdownLineInter: TREStateManager,
){
    var line: TRELine =
        TRECoreLine(this)
}