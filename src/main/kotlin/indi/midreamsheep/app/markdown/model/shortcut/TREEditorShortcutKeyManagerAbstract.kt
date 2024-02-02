package indi.midreamsheep.app.markdown.model.shortcut

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyDown
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.markdown.context.TREContext

abstract class TREEditorShortcutKeyManagerAbstract {
    private val pressKeys = HashSet<Long>()
    fun keyEvent(keyEvent: KeyEvent){
        if (keyEvent.type == KeyDown){
            pressKeys.add(keyEvent.key.keyCode)
        }else if (keyEvent.type == KeyUp){
            pressKeys.remove(keyEvent.key.keyCode)
        }
    }

    fun execute(context:TREContext){
        for (keyAction in getActions()) {
            for (key in keyAction.getKeys()) {
                if (key.size == pressKeys.size && pressKeys.containsAll(key)) {
                    keyAction.action(context)
                }
            }
        }
    }
    abstract fun getActions(): MutableList<TREShortcutKey>
}