package indi.midreamsheep.app.tre.shared.frame.engine.shortcut.shortcuts

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcut
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyWeakChecker
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.shortcut.TRELineShortcutHandler

@EditorShortcut
class UpShortcut: TRELineShortcutHandler {
    override fun action(context: TREEditorContext) {
        val stateManager = context.blockManager
        val xPositionData = XPositionData(stateManager.getCurrentBlock()!!.getComposeState().getPointerAbsoluteXPosition())
        stateManager.focusBlock(stateManager.getCurrentBlockIndex()-1, TREFocusEnum.IN_TARGET_POSITION_UP,xPositionData)
    }

    override fun getKeys(): List<TREShortcutKeyWeakChecker> {
        return listOf(
            TREShortcutKeyWeakChecker(
                Key.DirectionUp.keyCode
            )
        )
    }

    override fun isEnable(context: TREEditorContext): Boolean {
        val currentManager = context.blockManager
        return currentManager.getCurrentBlockIndex()!=0&&currentManager.getCurrentBlock()!!.getEditorShortcutState().isUpAvailable
    }
}