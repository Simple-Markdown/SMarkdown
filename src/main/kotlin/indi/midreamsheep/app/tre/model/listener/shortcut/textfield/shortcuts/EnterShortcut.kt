package indi.midreamsheep.app.tre.model.listener.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakMatch
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class EnterShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext?) {
        val stateManager = (context as TREEditorContext).editorFileManager.getStateManager()
        val wrapper = stateManager.getCurrentBlock() ?: return
        val textValue = (wrapper.line as TRETextBlock)

        val markdownLineStateList = stateManager.getTREBlockStateList()
        val index = markdownLineStateList.indexOf(wrapper)
        val newMarkdownLineState = TREBlockState(stateManager)

        markdownLineStateList.add(index+1,newMarkdownLineState)

        //数据更新
        val start = textValue.getTextFieldValue().selection.start
        val newLineText = textValue.getTextFieldValue().text.substring(start)

        (newMarkdownLineState.line as TRECoreBlock).content.value = TextFieldValue(newLineText)
        textValue.setTextFieldValue(TextFieldValue(textValue.getTextFieldValue().text.substring(0,start)))

        wrapper.line.releaseFocus()
        newMarkdownLineState.line.focus()
    }

    override fun isEnable(context: TREContext?): Boolean {
        if (context !is TREEditorContext){
            return false
        }
        val stateManager = context.editorFileManager.getStateManager()
        stateManager.getCurrentBlock() ?: return false
        return stateManager.getCurrentBlock()!!.line is TRETextBlock
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyWeakMatch(
                Key.Enter.keyCode
            )
        )
    }
}