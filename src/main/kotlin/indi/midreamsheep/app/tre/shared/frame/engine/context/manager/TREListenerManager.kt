package indi.midreamsheep.app.tre.shared.frame.engine.context.manager

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext

abstract class TREListenerManager(

) {
    lateinit var context: TREEditorContext

    abstract fun keyEvent(event: KeyEvent):Boolean
}