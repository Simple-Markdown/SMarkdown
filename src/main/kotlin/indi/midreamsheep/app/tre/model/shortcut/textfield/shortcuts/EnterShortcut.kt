package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class EnterShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext?) {
        val stateManager = (context as TREEditorContext).editorFileManager.getStateManager()
        val wrapper = stateManager.getCurrentMarkdownLineState() ?: return
        val textValue = (wrapper.line as TRETextLine)

        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        val index = markdownLineStateList.indexOf(wrapper)
        val newMarkdownLineState = TRELineState(stateManager)

        markdownLineStateList.add(index+1,newMarkdownLineState)

        //数据更新
        val start = textValue.getTextFieldValue().selection.start
        val newLineText = textValue.getTextFieldValue().text.substring(start)

        (newMarkdownLineState.line as CoreTRELine).content.value = TextFieldValue(newLineText)
        textValue.setTextFieldValue(TextFieldValue(textValue.getTextFieldValue().text.substring(0,start)))

        wrapper.line.releaseFocus()
        newMarkdownLineState.line.focus()
    }

    override fun isEnable(context: TREContext?): Boolean {
        if (context !is TREEditorContext){
            return false
        }
        val stateManager = context.editorFileManager.getStateManager()
        stateManager.getCurrentMarkdownLineState() ?: return false
        return true
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKey> {
        return listOf(
            TREShortcutKey(
                Key.Enter.keyCode
            )
        )
    }
}