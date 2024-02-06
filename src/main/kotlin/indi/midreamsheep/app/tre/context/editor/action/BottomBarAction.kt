package indi.midreamsheep.app.tre.context.editor.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class BottomBarAction(context: TREEditorContext): TREAction<TREEditorContext>(context) {
    fun setStateString(stateString:String){
        context.bottomBarViewModel.stateString.value = stateString
    }
}