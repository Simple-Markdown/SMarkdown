package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELine
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyEntity
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyWeakMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.model.shortcut.textfield.TextFileShortcutTool
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@TextFieldShortcutKey
class BackspaceShortcut: TREEditorShortcutKeyHandler() {

    @Injector
    private var textFileShortcutTool: TextFileShortcutTool? = null

    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()

        val currentState = stateManager.getCurrentMarkdownLine() ?: return
        val treTextLine = currentState.line as TRETextLine
        if (treTextLine.getTextFieldValue().selection.start == 0){
            val index = stateManager.getMarkdownLineStateList().indexOf(currentState)
            val lastLine = stateManager.getMarkdownLineStateList()[index-1].line as TRETextLine

            val lastLineContent = lastLine.getTextFieldValue().text
            lastLine.setTextFieldValue(TextFieldValue(
                text = lastLineContent+treTextLine.getTextFieldValue().text,
                selection = TextRange(lastLineContent.length)
            ))
            (lastLine as TRELine).focusFromLast()

            currentState.line.releaseFocus()
            stateManager.getMarkdownLineStateList().remove(currentState)
        }
    }

    override fun isEnable(context: TREContext?): Boolean {
        //检查传入的context是否是TREEditorContext
        if (!super.isEnable(context)) return false
        if (!textFileShortcutTool!!.check(context!!, true)) return false
        //检查当前行是否是TRETextLine
        val stateManager = (context as TREEditorContext).editorFileManager.getStateManager()
        val wrapper = stateManager.getCurrentMarkdownLine() ?: return false
        val index = stateManager.getMarkdownLineStateList().indexOf(wrapper)
        return stateManager.getMarkdownLineStateList()[index-1].line is TRETextLine
    }

    override fun getKeys(): List<TREShortcutKeyEntity> {
        return listOf(
            TREShortcutKeyWeakMatch(
                Key.Backspace.keyCode
            )
        )
    }
}