package indi.midreamsheep.app.tre.shared.frame.engine.render.listener

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext

abstract class TRERenderListener{

    abstract fun keyEvent(
        key:KeyEvent,
        context: TREEditorContext
    ):Boolean

    fun handleKeyEvent(
        key:KeyEvent,
        context: TREEditorContext
    ):Boolean {
        context.treTextFieldShortcutKeyManager.update((key))
        if(key.type != KeyEventType.KeyDown){
            return false
        }
        if (keyEvent(key,context)){
            return true
        }
        return context.treTextFieldShortcutKeyManager.keyEvent(key,context)
    }

}