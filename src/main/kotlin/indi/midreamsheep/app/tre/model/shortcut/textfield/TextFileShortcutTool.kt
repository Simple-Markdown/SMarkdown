package indi.midreamsheep.app.tre.model.shortcut.textfield

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TextFileShortcutTool {
    fun check(
        context:TREContext,
        start:Boolean
    ):Boolean{
        if(context !is TREEditorContext){
            return false
        }
        val stateManager = context.editorFileManager.getStateManager()
        val currentState = stateManager.getCurrentMarkdownLine()
        if (currentState!!.line !is TRETextLine){
            return false
        }
        var wrongIndex = 0
        if (!start){
            wrongIndex = stateManager.getMarkdownLineStateList().size-1

        }
        if (stateManager.getMarkdownLineStateList().indexOf(currentState)==wrongIndex){
            return false
        }
        var wrongSelection = 0
        if (!start){
            wrongSelection = (currentState.line as TRETextLine).getTextFieldValue().text.length
        }
        if ((currentState.line as TRETextLine).getTextFieldValue().selection.start!=wrongSelection){
            return false
        }
        return true
    }
}