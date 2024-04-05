package indi.midreamsheep.app.tre.model.parser.paragraph.head

import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.model.render.style.styletext.TREStyleTextTree

class HeadListener(
    private val line: TRECoreBlock,
    private val id:Long,
): TRERenderListener() {

    private var startIndex = 0

    override fun setStartIndex(startIndex: Int) {
        this.startIndex = startIndex
    }

    override fun getStartIndex(): Int {
        return startIndex
    }

    override fun keyEvent(
        key: KeyEvent,
        context: TREEditorContext,
        styleTextTree: TREStyleTextTree
    ): Boolean {
        //Enter
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Backspace.keyCode
                )
            )) {
            val treCoreBlock = context.editorFileManager.getStateManager().getCurrentBlock()!!.line as TRECoreBlock
            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val styleTextHeadRoot = styleTextTree as StyleTextHeadRoot
                val index = treCoreBlock.content.value.selection.start - (styleTextHeadRoot.level+1+getStartIndex())
                context.editorFileManager.getStateManager().executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = treCoreBlock.content.value.text.substring( styleTextHeadRoot.level+1+getStartIndex()),
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