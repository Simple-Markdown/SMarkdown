package indi.midreamsheep.app.markdown.model.shortcut

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.markdown.context.TREContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class TREEditorShortcutKeyManagerAbstract {
    private val pressKeys = HashSet<Long>()
    private var lastTime = System.currentTimeMillis()-1000
    private var lastShortcut:TREShortcutKey? = null
    @OptIn(DelicateCoroutinesApi::class)
    fun keyEvent(keyEvent: KeyEvent, context: TREContext,needExecute:Boolean): Boolean {
        if (keyEvent.type == KeyDown){
            pressKeys.add(keyEvent.key.keyCode)
            println("按下了"+keyEvent.key.keyCode)
        }else if (keyEvent.type == KeyUp){
            pressKeys.remove(keyEvent.key.keyCode)
            println("松开了"+keyEvent.key.keyCode)
            return false
        }
        if (!needExecute){
            return false
        }
        val (hasShortKey, shortcut) = execute()
        GlobalScope.launch {
            val nowTime = System.currentTimeMillis()
            if (lastShortcut==shortcut&&nowTime - lastTime < 1000){
                return@launch
            }
            if (shortcut!=null){
                shortcut.action(context)
                lastShortcut = shortcut
                lastTime = System.currentTimeMillis()
            }
        }
        return hasShortKey
    }

    @OptIn(ExperimentalComposeUiApi::class)
    private fun execute(): Pair<Boolean, TREShortcutKey?> {
        for (keyAction in getActions()) {
            for (key in keyAction.getKeys()) {
                if (key.size != pressKeys.size) {
                    continue
                }
                for (l in key) {
                    if (!pressKeys.contains(l)) {
                        return Pair(false,null)
                    }
                }
                lastShortcut = keyAction
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