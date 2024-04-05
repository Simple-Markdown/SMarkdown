package indi.midreamsheep.app.tre.model.render.listener

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.render.style.styletext.TREStyleTextTree

abstract class TRERenderListener{

    abstract fun keyEvent(
        key:KeyEvent,
        context: TREEditorContext,
        styleTextTree: TREStyleTextTree
    ):Boolean

    fun handleKeyEvent(
        key:KeyEvent,
        context: TREEditorContext,
        styleTextTree: TREStyleTextTree
    ):Boolean {
        context.treTextFieldShortcutKeyManager.update((key))
        if(key.type != KeyEventType.KeyDown){
            return false
        }
        if (keyEvent(key,context,styleTextTree)){
            return true
        }
        return context.treTextFieldShortcutKeyManager.keyEvent(key,context)
    }

    open fun setStartIndex(startIndex: Int) {  }

    open fun getStartIndex(): Int { return 0 }
}