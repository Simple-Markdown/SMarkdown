package indi.midreamsheep.app.tre.model.listener.shortcut.textfield.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlock
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakMatch
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.model.listener.shortcut.textfield.TextFileShortcutTool
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@TextFieldShortcutKey
class BackspaceShortcut: TREEditorShortcutKeyHandler() {

    @Injector
    private var textFileShortcutTool: TextFileShortcutTool? = null

    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()

        val currentState = stateManager.getCurrentBlock() ?: return
        val treTextLine = currentState.line as TRETextBlock
        if (treTextLine.getTextFieldValue().selection.start == 0){
            val index = stateManager.getTREBlockStateList().indexOf(currentState)
            val lastLine = stateManager.getTREBlockStateList()[index-1].line as TRETextBlock

            (lastLine as TREBlock).focusFromLast()
            val lastLineContent = lastLine.getTextFieldValue().text
            lastLine.setTextFieldValue(TextFieldValue(
                text = lastLineContent+treTextLine.getTextFieldValue().text,
                selection = TextRange(lastLineContent.length)
            ))

            currentState.line.releaseFocus()
            stateManager.getTREBlockStateList().remove(currentState)
        }
    }

    override fun isEnable(context: TREContext?): Boolean {
        //检查传入的context是否是TREEditorContext
        if (!super.isEnable(context)) return false
        if (!textFileShortcutTool!!.check(context!!, true)) return false
        //检查当前行是否是TRETextLine
        val stateManager = (context as TREEditorContext).editorFileManager.getStateManager()
        val wrapper = stateManager.getCurrentBlock() ?: return false
        val index = stateManager.getTREBlockStateList().indexOf(wrapper)
        return stateManager.getTREBlockStateList()[index-1].line is TRETextBlock
    }

    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyWeakMatch(
                Key.Backspace.keyCode
            )
        )
    }
}