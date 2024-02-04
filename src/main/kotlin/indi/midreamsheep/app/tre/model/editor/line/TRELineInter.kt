package indi.midreamsheep.app.tre.model.editor.line

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

interface TRELineInter {
    fun focus()
    fun focusFromLast(){
        focus()
    }
    fun focusFormStart(){
        focus()
    }
    fun releaseFocus()

    fun getComposable(context: TREEditorContext):@Composable ()->Unit
    fun getContent():String
}