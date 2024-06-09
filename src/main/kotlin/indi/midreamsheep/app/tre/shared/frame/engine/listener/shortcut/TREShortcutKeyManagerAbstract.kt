package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.tre.model.listener.shortcut.TREKeyboardKeyManager
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.api.context.TREContext

abstract class TREShortcutKeyManagerAbstract {

    abstract fun manager(): TREKeyboardKeyManager

    fun keyEvent(keyEvent: KeyEvent, context: TREContext): Boolean {
        if(!update(keyEvent)) return false
        val (hasShortKey, shortcut) = execute(context)
        if (hasShortKey){
            shortcut?.handle(context)
            return true
        }
        return false
    }

    private fun execute(context: TREContext): Pair<Boolean, TREShortcutKeyHandler?> {
        for (keyAction in getActions()) {
            for (key in keyAction.getKeys()) {
                if(!(check(key)&&keyAction.isEnable(context))){
                    continue
                }
                return Pair(true,keyAction)
            }
        }
        return Pair(false,null)
    }
    abstract fun getActions(): MutableList<TREShortcutKeyHandler>

    fun check(keyEntity: TREShortcutKeyChecker) = keyEntity.check(manager().pressedKeys)
    fun update(keyEvent: KeyEvent):Boolean{
        return when (keyEvent.type) {
            KeyDown -> {
                manager().addPressedKey(keyEvent.key.keyCode)
                true
            }
            KeyUp -> {
                manager().removePressedKey(keyEvent.key.keyCode)
                false
            }
            else -> {
                false
            }
        }
    }
}