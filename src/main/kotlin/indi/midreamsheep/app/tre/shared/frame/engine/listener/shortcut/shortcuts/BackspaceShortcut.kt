package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class BackspaceShortcut: TREEditorShortcutHandler {

    override fun action(context: TREEditorContext) {
        //获取blockManager
        val blockManager = context.blockManager
        //获取当前块的相关信息
        val currentBlock = blockManager.getCurrentBlock()!! as TRETextBlock
        val currentLineIndex = blockManager.indexOf(currentBlock)
        //获取上一格块的焦点
        blockManager.focusBlock(currentLineIndex-1,TREFocusEnum.IN_END)
        //对上一块进行数据处理
        var lastLine = blockManager.getTREBlock(currentLineIndex-1)
        while(lastLine is TREContextBlock){
            lastLine = lastLine.getBlockManager().getTREBlock(lastLine.getBlockManager().getSize()-1)
        }
        if (lastLine !is TRETextBlock) return
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
                        text = lastLineContent+currentBlock.getTextFieldValue().text,
                        selection = TextRange(lastLineContent.length)
                    ),
                    lastLine
                )
            )
        }
        //提交命令
        blockManager.executeOperator(treOperatorGroup)
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.blockManager
        val currentBlock = stateManager.getCurrentBlock()
        if (currentBlock==null||currentBlock !is TRETextBlock ||stateManager.getCurrentBlockIndex()==0){
            return false
        }
        return currentBlock.getShortcutState().isLeftAvailable
    }

    override fun getKeys(): List<TREShortcutKeyChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.Backspace.keyCode
            )
        )
    }
}