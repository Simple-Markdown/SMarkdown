package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class EnterShortcut: TREEditorShortcutHandler {
    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager

        val wrapper = stateManager.getCurrentBlock() ?: return
        val currentLine = (wrapper as TRETextBlock)
        val currentLineNumber = stateManager.indexOf(wrapper)

        val newLine = TRECoreBlock(stateManager)

        val start = currentLine.getTextFieldValue().selection.start
        val newLineText = currentLine.getTextFieldValue().text.substring(start)

        val treOperatorGroup = TREOperatorGroup().apply {
            addOperator(TREBlockInsert(currentLineNumber+1,newLine))
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
        stateManager.focusBlock(currentLineNumber+1)
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        return context.blockManager.getCurrentBlock()!! is TRETextBlock
    }

    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.Enter.keyCode
            )
        )
    }
}