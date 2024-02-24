package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyEntity
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class PasteShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext?) {
        if (context!!.clipboardAction.getClipboardContentType() != "Text") {
            return
        }
        //获取当前行
        val stateList = context.editorFileManager.getStateManager().getMarkdownLineStateList()

        val currentLine = context.editorFileManager.getStateManager().getCurrentMarkdownLine()!!.line as TRETextLine
        val clipboardContent = context.clipboardAction.getClipboardContent()

        val textFieldValue = currentLine.getTextFieldValue()
        //在指定位置插入内容
        val currentContent = currentLine.getTextFieldValue().text.subSequence(0, textFieldValue.selection.start).toString() +
                clipboardContent +
                currentLine.getTextFieldValue().text.subSequence(textFieldValue.selection.end, textFieldValue.text.length).toString()

        val currentLineNumber = stateList.indexOf(currentLine.getLineState())
        currentContent.split("\n").forEachIndexed { index, s ->
            if (index == 0) {
                currentLine.setTextFieldValue(TextFieldValue(s))
            } else {
                //在当前行的下面插入新的行
                val newState = TRELineState(context.editorFileManager.getStateManager())
                (newState.line as TRECoreLine).content.value = TextFieldValue(s)
                stateList.add(currentLineNumber+ index, newState)
            }
        }
        currentLine.focusFromLast()
    }

    override fun isEnable(context: TREContext?): Boolean {
        if (!super.isEnable(context)) return false
        val currentMarkdownLine =
            (context as TREEditorContext).editorFileManager.getStateManager().getCurrentMarkdownLine() ?: return false
        if (currentMarkdownLine.line !is TRETextLine) return false
        return true
    }

    /**
     * 获取快捷键的定义
     */
    override fun getKeys(): MutableList<TREShortcutKeyEntity> {
        return mutableListOf(
            TREShortcutKeyTotalMatch(
                Key.CtrlLeft.keyCode,
                Key.V.keyCode
            ),
            TREShortcutKeyTotalMatch(
                Key.CtrlRight.keyCode,
                Key.V.keyCode
            ),
        )
    }
}