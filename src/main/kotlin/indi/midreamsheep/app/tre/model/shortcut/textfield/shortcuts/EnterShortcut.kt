package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class EnterShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext?) {
        val stateManager = (context as TREEditorContext).editorFileManager.getStateManager()
        val wrapper = stateManager.getCurrentMarkdownLine() ?: return
        val textValue = (wrapper.line as TRETextLine)

        val markdownLineStateList = stateManager.getMarkdownLineStateList()
        val index = markdownLineStateList.indexOf(wrapper)
        val newMarkdownLineState = TRELineState(stateManager)

        markdownLineStateList.add(index+1,newMarkdownLineState)

        //数据更新
        val start = textValue.getTextFieldValue().selection.start
        val newLineText = textValue.getTextFieldValue().text.substring(start)

        (newMarkdownLineState.line as TRECoreLine).content.value = TextFieldValue(newLineText)
        textValue.setTextFieldValue(TextFieldValue(textValue.getTextFieldValue().text.substring(0,start)))

        wrapper.line.releaseFocus()
        newMarkdownLineState.line.focus()
    }

    override fun isEnable(context: TREContext?): Boolean {
        if (context !is TREEditorContext){
            return false
        }
        val stateManager = context.editorFileManager.getStateManager()
        stateManager.getCurrentMarkdownLine() ?: return false
        return stateManager.getCurrentMarkdownLine()!!.line is TRETextLine
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyTotalMatch> {
        return listOf(
            TREShortcutKeyTotalMatch(
                Key.Enter.keyCode
            )
        )
    }
}