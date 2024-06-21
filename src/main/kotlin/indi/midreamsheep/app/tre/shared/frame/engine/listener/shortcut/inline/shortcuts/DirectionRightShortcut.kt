package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts.tool.selectionInEnd

@TextFieldShortcutKey
class DirectionRightShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorWindowContext?) {
        val stateManager = context!!.treFileManager.getStateManager()
        val index = stateManager.getCurrentBlockIndex()
        stateManager.focusBlock(index+1){
            (it as TRETextBlock).focusFormStart()
        }
    }

    override fun isEnable(context: TREEditorWindowContext): Boolean {
        val stateManager = context.treFileManager.getStateManager()
        if(stateManager.getCurrentBlockIndex()==stateManager.getSize()-1){
            return false
        }
        return selectionInEnd(context)
    }

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionRight.keyCode
            )
        )
    }
}