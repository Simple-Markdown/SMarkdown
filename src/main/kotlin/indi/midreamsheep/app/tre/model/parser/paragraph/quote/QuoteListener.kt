package indi.midreamsheep.app.tre.model.parser.paragraph.quote

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.model.render.style.styletext.TREStyleTextTree

class QuoteListener(
    private val line: TRECoreBlock,
    private val id:Long,
    private val childListener: TRERenderListener,
): TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext,styleTextTree: TREStyleTextTree): Boolean {
        if (childListener.keyEvent(key, context,styleTextTree.getChildren()[0]!!)) return true
        //Enter
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Key.Backspace.keyCode
                )
            )) {
            val treCoreBlock = context.editorFileManager.getStateManager().getCurrentBlock()!!.line as TRECoreBlock
            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val styleTextQuoteRoot = styleTextTree as StyleTextQuoteRoot
                val index = treCoreBlock.content.value.selection.start - (styleTextQuoteRoot.level*2)
                context.editorFileManager.getStateManager().executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = treCoreBlock.content.value.text.substring( styleTextQuoteRoot.level*2),
                            selection = TextRange(index)
                        ),
                        context.editorFileManager.getStateManager().getTREBlockStateList().indexOf(treCoreBlock.lineState)
                    )
                )
                line.propertySet.add(id)
                return true
            }
        }
        return false
    }
}