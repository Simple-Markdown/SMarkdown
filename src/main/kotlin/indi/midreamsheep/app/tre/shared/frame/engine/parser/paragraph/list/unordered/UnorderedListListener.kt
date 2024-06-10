package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.unordered

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts.EnterShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.listener.TRERenderListener

class UnorderedListListener(
    private val line: TRECoreBlock,
    private val styleTextTree: StyleTextUnorderedListRoot
): TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext): Boolean {
        val stateManager = context.editorFileManager.getStateManager()
        val lineNumber = stateManager.indexOf(line)
        //Enter
        val treCoreBlock = stateManager.getCurrentBlock()!! as TRECoreBlock
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Key.Backspace.keyCode
                )
            )) {
/*            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val index = treCoreBlock.content.value.selection.start - 2
                val start = styleTextTree.getOriginalRange().getStart()
                stateManager.executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = treCoreBlock.content.value.text.removeRange(start,start+2),
                            selection = TextRange(index)
                        ),
                        stateManager.indexOf(treCoreBlock)
                    )
                )
                return true
            }*/
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
        val nextLine = stateManager.getTREBlock(lineNumber+1) as TRECoreBlock
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