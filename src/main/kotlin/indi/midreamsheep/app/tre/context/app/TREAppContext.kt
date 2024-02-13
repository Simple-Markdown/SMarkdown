package indi.midreamsheep.app.tre.context.app

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.app.action.RecentFileAction
import indi.midreamsheep.app.tre.context.app.action.WindowAction
import indi.midreamsheep.app.tre.context.app.viewmodel.RecentFileViewModel
import indi.midreamsheep.app.tre.context.app.viewmodel.WindowViewModel

/**
 * 核心上下文，用于windows的响应和数据的传递
 * */
class TREAppContext: TREContext {

    companion object {
        val context = TREAppContext()
    }

    /*ViewModel*/
    val windowViewModel = WindowViewModel(this)
    val recentFileViewModel = RecentFileViewModel(this)

    /*action*/
    val windowAction = WindowAction(this)
    val recentFileAction = RecentFileAction(this)
}