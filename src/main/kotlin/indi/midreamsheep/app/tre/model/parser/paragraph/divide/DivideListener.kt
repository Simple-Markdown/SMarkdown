package indi.midreamsheep.app.tre.model.parser.paragraph.divide

import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyWeakMatch

class DivideListener: TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext): Boolean {
        //Enter
        if (context.shortcutAction.textFiledCheck(TREShortcutKeyWeakMatch(Enter.keyCode))) {
            val stateManager = context.editorFileManager.getStateManager()
            val markdownLineStateList = stateManager.getMarkdownLineStateList()
            val currentMarkdownLineState = stateManager.getCurrentMarkdownLineState().value!!
            val index = markdownLineStateList.indexOf(currentMarkdownLineState)
            val newMarkdownLineState = TRELineState(stateManager)
            markdownLineStateList.add(index + 1, newMarkdownLineState)
            val treCoreLine = newMarkdownLineState.line as TRECoreLine
            treCoreLine.content.value = TextFieldValue(
                text = "- ",
                selection = TextRange(2)
            )
            treCoreLine.buildContent(stateManager)
            currentMarkdownLineState.line.releaseFocus()
            newMarkdownLineState.line.focus()
            return true
        }
        return false
    }
}