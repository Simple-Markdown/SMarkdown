package indi.midreamsheep.app.tre.model.shortcut

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.tre.context.TREContext

abstract class TREEditorShortcutKeyManagerAbstract {
    private val pressKeys = HashSet<Long>()
    fun keyEvent(keyEvent: KeyEvent, context: TREContext): Boolean {
        if(!update(keyEvent)) return false
        val (hasShortKey, shortcut) = execute(context)
        if (hasShortKey){
            shortcut?.action(context)
            clear()
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

    fun clear() = pressKeys.clear()
    fun check(keyEntity: TREShortcutKeyEntity) = keyEntity.check(pressKeys)
    fun update(keyEvent: KeyEvent):Boolean{
        return if (keyEvent.type == KeyDown){
            pressKeys.add(keyEvent.key.keyCode)
            true
        }else{
            pressKeys.remove(keyEvent.key.keyCode)
            false
        }
    }
}