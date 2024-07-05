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
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class EnterShortcut: TREEditorShortcutHandler {
    override fun action(context: TREEditorContext) {
        //获取块管理器
        val blockManager = context.blockManager

        //获取当前块及其信息
        val currentTextBlock =blockManager.getCurrentBlock()!! as TRETextBlock
        val currentLineIndex = blockManager.getCurrentBlockIndex()

        //创建下一行信息
        val newBlock = TRECoreBlock(blockManager)
        //获取下一行文本
        val start = currentTextBlock.getTextFieldValue().selection.start
        val newLineText = currentTextBlock.getTextFieldValue().text.substring(start)

        //组合命令
        val treOperatorGroup = TREOperatorGroup().apply {
            addOperator(TREBlockInsert(currentLineIndex+1,newBlock))
            addOperator(TREContentChange(
                TextFieldValue(),
                TextFieldValue(newLineText),
                currentLineIndex+1
            ))
            addOperator(
                TREContentChange(
                    currentTextBlock.getTextFieldValue(),
                    currentTextBlock.getTextFieldValue().copy(
                        text = currentTextBlock.getTextFieldValue().text.substring(0,start),
                    ),
                    currentLineIndex
                )
            )
        }
        //提交命令
        blockManager.executeOperator(treOperatorGroup)
        blockManager.focusBlock(currentLineIndex+1,getId())
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