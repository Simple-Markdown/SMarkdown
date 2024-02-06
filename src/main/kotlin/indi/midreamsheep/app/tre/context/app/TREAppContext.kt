package indi.midreamsheep.app.tre.context.app

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.app.action.WindowAction
import indi.midreamsheep.app.tre.context.app.viewmodel.WindowViewModel
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

class TREAppContext: TREContext {
    /*ViewModel*/
    val windowViewModel = WindowViewModel(this)
    /*action*/
    val windowAction = WindowAction(this)
}