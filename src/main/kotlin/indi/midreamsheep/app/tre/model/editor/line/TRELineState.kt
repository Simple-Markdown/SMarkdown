package indi.midreamsheep.app.tre.model.editor.line

import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager

class TRELineState(val markdownLineInter: TREStateManager){
    var line: TRELineInter =
        CoreTRELine(this)
}