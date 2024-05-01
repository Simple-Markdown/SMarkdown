package indi.midreamsheep.app.tre.model.listener.shortcut.textfield

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock
import indi.midreamsheep.app.tre.model.editor.block.TRECoreBlock
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
        val currentState = stateManager.getCurrentBlock() ?: return false
        if (currentState.line !is TRETextBlock){
            return false
        }
        var wrongIndex = 0
        if (!start){
            wrongIndex = stateManager.getTREBlockStateList().size-1

        }
        if (stateManager.getTREBlockStateList().indexOf(currentState)==wrongIndex){
            return false
        }
        var wrongSelection = 0
        if(currentState.line is TRECoreBlock){
            wrongSelection = (currentState.line as TRECoreBlock).render.value.offsetMap.getStartOffset()
        }
        if (!start){
            wrongSelection = (currentState.line as TRETextBlock).getTextFieldValue().text.length
        }
        if ((currentState.line as TRETextBlock).getTextFieldValue().selection.start!=wrongSelection){
            return false
        }
        return true
    }
}