package indi.midreamsheep.app.tre.model.shortcut

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.tre.context.TREContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class TREEditorShortcutKeyManagerAbstract {
    private val pressKeys = HashSet<Long>()
    @OptIn(DelicateCoroutinesApi::class)
    fun keyEvent(keyEvent: KeyEvent, context: TREContext,needExecute:Boolean): Boolean {
        if (keyEvent.type == KeyDown){
            pressKeys.add(keyEvent.key.keyCode)
            if (!needExecute){
                return false
            }
            val (hasShortKey, shortcut) = execute()
            GlobalScope.launch {
                shortcut?.action(context)
            }
            return hasShortKey
        }else if (keyEvent.type == KeyUp){
            pressKeys.remove(keyEvent.key.keyCode)
        }
        return false
    }

    private fun execute(): Pair<Boolean, TREShortcutKey?> {
        for (keyAction in getActions()) {
            for (key in keyAction.getKeys()) {
                if (key.size != pressKeys.size) {
                    continue
                }
                var isMatch = true
                for (l in key) {
                    if (!pressKeys.contains(l)) {
                        isMatch = false
                    }
                }
                if (!isMatch) {
                    continue
                }
                return Pair(true,keyAction)
            }
        }
        return Pair(false,null)
    }
    abstract fun getActions(): MutableList<TREShortcutKey>
    fun clear() {
        pressKeys.clear()
    }
}