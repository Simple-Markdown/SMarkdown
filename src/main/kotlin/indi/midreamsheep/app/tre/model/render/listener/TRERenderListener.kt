package indi.midreamsheep.app.tre.model.render.listener

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

abstract class TRERenderListener{

    abstract fun keyEvent(key:KeyEvent,context: TREEditorContext):Boolean

    fun handleKeyEvent(
        key:KeyEvent,
        context: TREEditorContext
    ):Boolean {
        context.treTextFieldShortcutKeyManager.update((key))
        if (keyEvent(key,context)){
            context.treTextFieldShortcutKeyManager.clear()
            return true
        }
        return context.treTextFieldShortcutKeyManager.keyEvent(key,context)
    }

}