package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.model.shortcut.textfield.TextFileShortcutTool
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@TextFieldShortcutKey
class BackspaceShortcut: TREEditorShortcutKeyHandler() {

    @Injector
    private var textFileShortcutTool: TextFileShortcutTool? = null

    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val wrapper = stateManager.getCurrentMarkdownLineState() ?: return
        val index = stateManager.getMarkdownLineStateList().indexOf(wrapper)
        val treTextLine = wrapper.line as TRETextLine
        if (treTextLine.getTextFieldValue().selection.start == 0){
            val lastLine = stateManager.getMarkdownLineStateList()[index-1].line as CoreTRELine
            lastLine.focus()
            val lastLineContent = lastLine.content.value.text
            lastLine.content.value = TextFieldValue(
                text = lastLineContent+treTextLine.getTextFieldValue().text,
                selection = TextRange(lastLineContent.length)
            )
            wrapper.line.releaseFocus()
            stateManager.getMarkdownLineStateList().remove(wrapper)
        }
    }

    override fun isEnable(context: TREContext?): Boolean {
        return textFileShortcutTool!!.check(context!!, true)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKey> {
        return listOf(
            TREShortcutKey(
                Key.Backspace.keyCode
            )
        )
    }
}