package indi.midreamsheep.app.tre.model.render.listener

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

abstract class TRERenderListener{

    abstract fun keyEvent(key:KeyEvent,context: TREEditorContext):Boolean

    fun handleKeyEvent(
        key:KeyEvent,
        context: TREEditorContext
    ):Boolean {
        context.shortcutAction.textFieldUpdate(key)
        if (keyEvent(key,context)){
            context.shortcutAction.textFieldEventClear()
            return true
        }
        return context.shortcutAction.textFieldEvent(key)
    }

}