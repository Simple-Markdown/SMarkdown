package indi.midreamsheep.app.tre.model.shortcut

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.tre.context.TREContext

abstract class TREEditorShortcutKeyManagerAbstract {
    private val pressKeys = HashSet<Long>()
    fun keyEvent(keyEvent: KeyEvent, context: TREContext,needExecute:Boolean): Boolean {
        if (keyEvent.type == KeyDown){
            pressKeys.add(keyEvent.key.keyCode)
            if (!needExecute){
                return false
            }
            val (hasShortKey, shortcut) = execute(context)
            shortcut?.action(context)
            return hasShortKey
        }else if (keyEvent.type == KeyUp){
            pressKeys.remove(keyEvent.key.keyCode)
        }
        return false
    }

    private fun execute(context: TREContext): Pair<Boolean, TREShortcutKeyHandler?> {
        for (keyAction in getActions()) {
            for (key in keyAction.getKeys()) {
                if(!key.check(pressKeys)||!keyAction.isEnable(context)){
                    continue
                }
                return Pair(true,keyAction)
            }
        }
        return Pair(false,null)
    }
    abstract fun getActions(): MutableList<TREShortcutKeyHandler>
    fun clear() {
        pressKeys.clear()
    }
}