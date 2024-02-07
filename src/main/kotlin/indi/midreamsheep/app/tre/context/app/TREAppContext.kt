package indi.midreamsheep.app.tre.context.app

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.app.action.WindowAction
import indi.midreamsheep.app.tre.context.app.viewmodel.WindowViewModel

class TREAppContext: TREContext {

    companion object {
        val context = TREAppContext()
    }

    /*ViewModel*/
    val windowViewModel = WindowViewModel(this)
    /*action*/
    val windowAction = WindowAction(this)
}