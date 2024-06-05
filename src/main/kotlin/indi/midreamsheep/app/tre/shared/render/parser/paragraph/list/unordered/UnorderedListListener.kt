package indi.midreamsheep.app.tre.shared.render.parser.paragraph.list.unordered

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts.EnterShortcut
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.render.listener.TRERenderListener

class UnorderedListListener(
    private val line: TRECoreBlock,
    private val id:Long,
    private val styleTextTree: StyleTextUnorderedListRoot
): TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext): Boolean {
        val stateManager = context.editorFileManager.getStateManager()
        val lineNumber = stateManager.getTREBlockStateList().indexOf(line.lineState)
        //Enter
        val treCoreBlock = stateManager.getCurrentBlock()!!.block as TRECoreBlock
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Key.Backspace.keyCode
                )
            )) {
            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val index = treCoreBlock.content.value.selection.start - 2
                val start = styleTextTree.getOriginalRange().getStart()
                stateManager.executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = treCoreBlock.content.value.text.removeRange(start,start+2),
                            selection = TextRange(index)
                        ),
                        stateManager.getTREBlockStateList().indexOf(treCoreBlock.lineState)
                    )
                )
                return true
            }
        }
        if(context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyWeakChecker(
                    Key.Enter.keyCode
                )
            )){
            getBean(EnterShortcut::class.java).handle(context)
            nextLineDecoration(stateManager,lineNumber)
            return true
        }
        return false
    }

    private fun nextLineDecoration(
        stateManager: TREBlockManager,
        lineNumber: Int
    ){
        val nextLine = stateManager.getTREBlockStateList()[lineNumber+1].block as TRECoreBlock
        val treContentChange = TREContentChange(
            nextLine.content.value,
            nextLine.content.value.copy(
                text = "- "+nextLine.content.value.text,
                selection = TextRange(nextLine.content.value.selection.start+2)
            ),
            lineNumber + 1
        )
        stateManager.executeOperator(treContentChange)
    }
}