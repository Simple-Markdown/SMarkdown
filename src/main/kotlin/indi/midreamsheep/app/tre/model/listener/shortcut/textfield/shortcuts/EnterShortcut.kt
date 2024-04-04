package indi.midreamsheep.app.tre.model.listener.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakMatch
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class EnterShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val stateList = stateManager.getTREBlockStateList()

        val wrapper = stateManager.getCurrentBlock() ?: return
        val currentLine = (wrapper.line as TRETextBlock)
        val currentLineNumber = stateList.indexOf(wrapper)

        val newLine = TREBlockState(stateManager)

        val start = currentLine.getTextFieldValue().selection.start
        val newLineText = currentLine.getTextFieldValue().text.substring(start)

        val treOperatorGroup = TREOperatorGroup().apply {
            addOperator(TREBlockInsert(currentLineNumber+1,newLine.line))
            addOperator(TREContentChange(
                TextFieldValue(),
                TextFieldValue(newLineText),
                currentLineNumber+1
            ))
            addOperator(
                TREContentChange(
                    currentLine.getTextFieldValue(),
                    currentLine.getTextFieldValue().copy(
                        text = currentLine.getTextFieldValue().text.substring(0,start),
                    ),
                    currentLineNumber
                )
            )
        }

        stateManager.executeOperator(treOperatorGroup)

        wrapper.line.releaseFocus()
        newLine.line.focus()
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