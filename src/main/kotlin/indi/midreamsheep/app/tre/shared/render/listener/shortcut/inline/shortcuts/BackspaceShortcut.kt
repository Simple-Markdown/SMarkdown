package indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.render.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts.tool.selectionInStart

@TextFieldShortcutKey
class BackspaceShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
        val stateManager = context.editorFileManager.getStateManager()

        val currentState = stateManager.getCurrentBlock()!!

        val treTextLine = currentState.block as TRETextBlock

        val currentLineIndex = stateManager.getTREBlockStateList().indexOf(currentState)

        stateManager.focusBlock(currentLineIndex-1){
            it.block.focusFromLast()
        }

        val lastLine = stateManager.getTREBlockStateList()[currentLineIndex-1].block as TRETextBlock
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
        val stateManager = context.editorFileManager.getStateManager()
        val currentBlock = stateManager.getCurrentBlock()
        val index = stateManager.getTREBlockStateList().indexOf(currentBlock)
        if(index==0){
            return false
        }
        return (stateManager.getTREBlock(index - 1).block is TRETextBlock)&& selectionInStart(context)
    }

    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.Backspace.keyCode
            )
        )
    }
}