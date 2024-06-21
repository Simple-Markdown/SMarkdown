package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.desktop.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext

class BottomBarAction(context: TREEditorWindowContext): TREAction<TREEditorWindowContext>(context) {
    fun setStateString(stateString:String){
        context.bottomBarViewModel.stateString.value = stateString
    }
}