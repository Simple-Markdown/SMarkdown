package indi.midreamsheep.app.markdown.model.editor.line

import indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager

class TRELineState(val markdownLineInter: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager){
    var line: indi.midreamsheep.app.markdown.model.editor.line.TRELineInter =
        indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine(this)
}