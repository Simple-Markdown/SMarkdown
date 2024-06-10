package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRETextBlock

@TextFieldShortcutKey
class PasteShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorContext?) {
        if (context!!.clipboardAction.getClipboardContentType() != "Text") {
            return
        }
        //获取当前行
        val stateList = context.editorFileManager.getStateManager()

        val currentLine = context.editorFileManager.getStateManager().getCurrentBlock()!! as TRETextBlock
        val clipboardContent = context.clipboardAction.getClipboardContent()

        val textFieldValue = currentLine.getTextFieldValue()
        //在指定位置插入内容
        val currentContent = currentLine.getTextFieldValue().text.subSequence(0, textFieldValue.selection.start).toString() +
                clipboardContent +
                currentLine.getTextFieldValue().text.subSequence(textFieldValue.selection.end, textFieldValue.text.length).toString()

        val currentLineNumber = stateList.indexOf(currentLine)
        currentContent.split("\n").forEachIndexed { index, s ->
            if (index == 0) {
                currentLine.setTextFieldValue(TextFieldValue(s))
            } else {
                //在当前行的下面插入新的行
                val newState = TRECoreBlock(context.editorFileManager.getStateManager())
                newState.content.value = TextFieldValue(s)
                stateList.addBlock(currentLineNumber+ index, newState)
            }
        }
        currentLine.focusFromLast()
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val currentMarkdownLine =
            context.editorFileManager.getStateManager().getCurrentBlock() ?: return false
        return currentMarkdownLine is TRETextBlock
    }

    /**
     * 获取快捷键的定义
     */
    override fun getKeys(): MutableList<TREShortcutKeyChecker> {
        return mutableListOf(
            TREShortcutKeyStrongChecker(
                Key.CtrlLeft.keyCode,
                Key.V.keyCode
            ),
            TREShortcutKeyStrongChecker(
                Key.CtrlRight.keyCode,
                Key.V.keyCode
            ),
        )
    }
}