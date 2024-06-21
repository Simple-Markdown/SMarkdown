package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler

@TextFieldShortcutKey
class UpShortcut: TREEditorShortcutKeyHandler() {
    override fun action(context: TREEditorWindowContext) {
        val stateManager = context.treFileManager.getStateManager()
        stateManager.focusBlock(stateManager.getCurrentBlockIndex()-1)
    }

    override fun getKeys(): List<TREShortcutKeyWeakChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.DirectionUp.keyCode
            )
        )
    }

    override fun isEnable(context: TREEditorWindowContext): Boolean {
        return context.treFileManager.getStateManager().getCurrentBlockIndex() != 0
    }
}