package indi.midreamsheep.app.tre.context.editor.action

import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class BottomBarAction(private val context: TREEditorContext) {
    fun setStateString(stateString:String){
        context.bottomBarViewModel.stateString.value = stateString
    }
}