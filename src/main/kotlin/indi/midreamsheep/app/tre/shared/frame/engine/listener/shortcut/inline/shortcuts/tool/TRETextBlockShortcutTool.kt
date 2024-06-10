package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts.tool

import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRETextBlock

fun selectionInStart(
    context: TREEditorContext,
):Boolean{
    val stateManager = context.editorFileManager.getStateManager()
    stateManager.getCurrentBlock()?.let {
        if (it !is TRETextBlock){
            return false
        }
        return it.getTextFieldValue().selection.start == it.getTextFieldRange().getStart()
    }
    return false
}

fun selectionInEnd(
    context: TREEditorContext,
):Boolean{
    val stateManager = context.editorFileManager.getStateManager()
    stateManager.getCurrentBlock()?.let {
        if (it !is TRETextBlock){
            return false
        }
        val treTextBlock = it
        val textField = treTextBlock.getTextFieldValue()
        val end = if(treTextBlock.getTextFieldRange().getEnd() == -1) textField.text.length else treTextBlock.getTextFieldRange().getEnd()
        return textField.selection.start == end
    }
    return false
}