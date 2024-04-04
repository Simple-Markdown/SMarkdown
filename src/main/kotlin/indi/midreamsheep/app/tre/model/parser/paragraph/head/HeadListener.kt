package indi.midreamsheep.app.tre.model.parser.paragraph.head

import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.render.listener.TRERenderListener

class HeadListener(
    private val line: TRECoreBlock,
    private val id:Long
): TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext): Boolean {
        //Enter
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Backspace.keyCode
                )
            )) {
            val treCoreBlock = context.editorFileManager.getStateManager().getCurrentBlock()!!.line as TRECoreBlock
            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val styleTextHeadRoot = treCoreBlock.render.value.styleText.styleTextTree!! as StyleTextHeadRoot
                val index = treCoreBlock.content.value.selection.start - (styleTextHeadRoot.level+1)
                context.editorFileManager.getStateManager().executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = treCoreBlock.content.value.text.substring( styleTextHeadRoot.level+1),
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