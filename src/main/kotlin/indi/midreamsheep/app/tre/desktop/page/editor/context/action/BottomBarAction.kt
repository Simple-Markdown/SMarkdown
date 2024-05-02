package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.shared.api.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext

class BottomBarAction(context: TREEditorContext): TREAction<TREEditorContext>(context) {
    fun setStateString(stateString:String){
        context.bottomBarViewModel.stateString.value = stateString
    }
}