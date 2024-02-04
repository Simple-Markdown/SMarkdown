package indi.midreamsheep.app.tre.model.main.pages

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.context.main.TREMainPageContext

interface TREMainPageButton {
    fun getComposable(context: TREMainPageContext): @Composable () -> Unit
}