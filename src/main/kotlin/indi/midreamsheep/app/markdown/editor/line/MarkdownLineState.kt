package indi.midreamsheep.app.markdown.editor.line

import indi.midreamsheep.app.markdown.editor.line.core.CoreMarkdownLine
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

class MarkdownLineState(val markdownLineInter: MarkdownStateManager){
    var line:MarkdownLineInter = CoreMarkdownLine(this)
}