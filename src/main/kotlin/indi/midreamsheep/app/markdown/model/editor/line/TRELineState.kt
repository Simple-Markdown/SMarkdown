package indi.midreamsheep.app.markdown.model.editor.line

import indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager

class TRELineState(val markdownLineInter: TREStateManager){
    var line: TRELineInter =
        CoreTRELine(this)
}