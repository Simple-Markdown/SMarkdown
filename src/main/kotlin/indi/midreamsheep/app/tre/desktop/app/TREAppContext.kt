package indi.midreamsheep.app.tre.desktop.app

import indi.midreamsheep.app.tre.shared.api.context.TREContext
import indi.midreamsheep.app.tre.desktop.app.action.RecentFileAction
import indi.midreamsheep.app.tre.desktop.app.viewmodel.RecentFileViewModel

/**
 * 核心上下文，用于windows的响应和数据的传递
 * */
class TREAppContext: TREContext {

    companion object {
        val context = TREAppContext()
    }

    /*ViewModel*/
    val recentFileViewModel = RecentFileViewModel(this)

    /*action*/
    val recentFileAction = RecentFileAction(this)
}