package indi.midreamsheep.app.markdown.model.main.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import indi.midreamsheep.app.markdown.context.main.TREMainPageContext

interface TREMainPageButton {
    fun getComposable(context: TREMainPageContext): @Composable () -> Unit
}