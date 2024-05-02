package indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlock
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

        val currentState = stateManager.getCurrentBlock() ?: return
        val treTextLine = currentState.line as TRETextBlock

        val currentLine = stateManager.getTREBlockStateList().indexOf(currentState)
        val lastLine = stateManager.getTREBlockStateList()[currentLine-1].line as TRETextBlock
        (lastLine as TREBlock).focusFromLast()
        val lastLineContent = lastLine.getTextFieldValue().text

        val treOperatorGroup = TREOperatorGroup()

        //提交删除当前行
        treOperatorGroup.addOperator(
            TREBlockDelete(
                currentLine
            )
        )

        treOperatorGroup.addOperator(
            TREContentChange(
                lastLine.getTextFieldValue(),
                TextFieldValue(
                    text = lastLineContent+treTextLine.getTextFieldValue().text,
                    selection = TextRange(lastLineContent.length)
                ),
                stateManager.getTREBlockStateList().indexOf(lastLine.lineState)

            )
        )
        stateManager.executeOperator(treOperatorGroup)
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        return selectionInStart(context)
    }

    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.Backspace.keyCode
            )
        )
    }
}