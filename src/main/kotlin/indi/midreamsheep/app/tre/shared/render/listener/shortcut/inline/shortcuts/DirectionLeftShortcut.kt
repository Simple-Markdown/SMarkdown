package indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.shortcuts.tool.selectionInStart

@TextFieldShortcutKey
class DirectionLeftShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val treTextLine = stateManager.getCurrentBlock()
        val index = stateManager.getTREBlockStateList().indexOf(treTextLine)
        stateManager.focusBlock(index-1){
            it.block.focusFromLast()
        }
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.editorFileManager.getStateManager()
        if(stateManager.getCurrentBlockIndex()==0){
            return false
        }
        return selectionInStart(context)
    }

    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionLeft.keyCode
            )
        )
    }
}