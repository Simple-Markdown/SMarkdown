package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts.tool.selectionInEnd

@TextFieldShortcutKey
class DirectionRightShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val index = stateManager.getCurrentBlockIndex()
        stateManager.focusBlock(index+1){
            it.focusFormStart()
        }
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val stateManager = context.editorFileManager.getStateManager()
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