package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

@EditorShortcut
class UpShortcut: TREEditorShortcutHandler {
    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        if(stateManager.getCurrentBlock() is TRETextBlock){
            stateManager.focusBlock(stateManager.getCurrentBlockIndex()-1){
                (it as TRETextBlock).focusFromLast()
            }
            return
        }
        stateManager.focusBlock(stateManager.getCurrentBlockIndex()-1)
    }

    override fun getKeys(): List<TREShortcutKeyWeakChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.DirectionUp.keyCode
            )
        )
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        return context.blockManager.getCurrentBlockIndex() != 0
    }
}