package indi.midreamsheep.app.tre.shared.render.parser.paragraph.head

import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTree

class HeadListener(
    private val line: TRECoreBlock,
    private val id:Long,
    private val styleTextTree: StyleTextHeadRoot
): TRERenderListener() {

    override fun keyEvent(
        key: KeyEvent,
        context: TREEditorContext
    ): Boolean {
        //Enter
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Backspace.keyCode
                )
            )) {
            val treCoreBlock = context.editorFileManager.getStateManager().getCurrentBlock()!!.block as TRECoreBlock
            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val index = treCoreBlock.content.value.selection.start - (styleTextTree.level + 1)
                val text = subString(treCoreBlock.content.value.text, getStartIndex(styleTextTree), getStartIndex(styleTextTree)+styleTextTree.level+1)
                context.editorFileManager.getStateManager().executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = text,
                            selection = TextRange(index)
                        ),
                        context.editorFileManager.getStateManager().getTREBlockStateList().indexOf(treCoreBlock.lineState)
                    )
                )
                return true
            }
        }
        return false
    }

    private fun getStartIndex(styleTextTree: TREStyleTextTree):Int{
        return styleTextTree.getOriginalStartOffset()
    }

    private fun subString(text: String, start: Int,end: Int):String{
        val startStr = text.substring(0,start)
        val endStr = text.substring(end)
        return startStr+endStr
    }

}