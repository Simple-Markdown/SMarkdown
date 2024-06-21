package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.tool.selectionInStart

@TextFieldShortcutKey
class BackspaceShortcut: TREEditorShortcutHandler {

    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager

        val currentState = stateManager.getCurrentBlock()!!

        val treTextLine = currentState as TRETextBlock

        val currentLineIndex = stateManager.indexOf(currentState)

        stateManager.focusBlock(currentLineIndex-1){
            (it as TRETextBlock).focusFromLast()
        }

        val lastLine = stateManager.getTREBlock(currentLineIndex-1) as TRETextBlock
        val lastLineContent = lastLine.getTextFieldValue().text

        val treOperatorGroup = TREOperatorGroup().apply {
            addOperator(
                TREBlockDelete(
                    currentLineIndex
                )
            )
            addOperator(
                TREContentChange(
                    lastLine.getTextFieldValue(),
                    TextFieldValue(
                        text = lastLineContent+treTextLine.getTextFieldValue().text,
                        selection = TextRange(lastLineContent.length)
                    ),
                    currentLineIndex - 1
                )
            )
        }

        stateManager.executeOperator(treOperatorGroup)
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.blockManager
        val index = stateManager.getCurrentBlockIndex()
        if(index==0){
            return false
        }
        return (stateManager.getTREBlock(index - 1) is TRETextBlock)&& selectionInStart(context)
    }

    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.Backspace.keyCode
            )
        )
    }
}