package indi.midreamsheep.app.tre.context.editor.action

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class ShortcutAction(context:TREEditorContext): TREAction<TREEditorContext>(context) {
    fun editorEvent(keyEvent: KeyEvent): Boolean{
        return context.shortcutViewModel.editorShortcutKeyManager.keyEvent(keyEvent,context,true)
    }

    fun updateEditorEvent(keyEvent: KeyEvent){
        context.shortcutViewModel.editorShortcutKeyManager.keyEvent(keyEvent,context,false)
    }

    fun textFieldEvent(keyEvent: KeyEvent): Boolean{
        return context.shortcutViewModel.textFieldShortcutKeyManager.keyEvent(keyEvent,context,true)
    }

    fun updateTextFieldEvent(keyEvent: KeyEvent){
        context.shortcutViewModel.textFieldShortcutKeyManager.keyEvent(keyEvent,context,false)
    }

}