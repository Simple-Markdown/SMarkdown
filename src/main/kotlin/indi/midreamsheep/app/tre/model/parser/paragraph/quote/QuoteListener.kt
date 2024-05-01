package indi.midreamsheep.app.tre.model.parser.paragraph.quote

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.textfield.shortcuts.EnterShortcut
import indi.midreamsheep.app.tre.shared.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.tool.ioc.getBean

class QuoteListener(
    private val line: TRECoreBlock,
    private val id:Long,
    private val childListener: TRERenderListener,
    private val styleTextTree: StyleTextQuoteRoot
): TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext): Boolean {
        val stateManager = context.editorFileManager.getStateManager()
        val startSize = stateManager.getTREBlockStateList().size
        val lineNumber = stateManager.getTREBlockStateList().indexOf(line.lineState)
        if (childListener.keyEvent(key, context)) {
            if(startSize!=stateManager.getTREBlockStateList().size){
                nextLineDecoration(stateManager,lineNumber)
            }
            return true
        }
        //Enter
        val treCoreBlock = stateManager.getCurrentBlock()!!.line as TRECoreBlock
        if (context.treTextFieldShortcutKeyManager.check(
                TREShortcutKeyStrongChecker(
                    Key.Backspace.keyCode
                )
            )) {
            if (treCoreBlock.render.value.offsetMap.getStartOffset()==treCoreBlock.content.value.selection.start){
                val index = treCoreBlock.content.value.selection.start - 2
                stateManager.executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = treCoreBlock.content.value.text.substring( 2),
                            selection = TextRange(index)
                        ),
                        stateManager.getTREBlockStateList().indexOf(treCoreBlock.lineState)
                    )
                )
                line.propertySet.add(id)
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
        stateManager: TREStateManager,
        lineNumber: Int
    ){
        val nextLine = stateManager.getTREBlockStateList()[lineNumber+1].line as TRECoreBlock
        val treContentChange = TREContentChange(
            nextLine.content.value,
            nextLine.content.value.copy(
                text = "> ".repeat(styleTextTree.level)+nextLine.content.value.text,
                selection = TextRange(nextLine.content.value.selection.start + styleTextTree.level*2)
            ),
            lineNumber + 1
        )
        stateManager.executeOperator(treContentChange)
    }
}