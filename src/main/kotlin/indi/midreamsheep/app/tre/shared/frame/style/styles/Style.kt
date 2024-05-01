package indi.midreamsheep.app.tre.shared.frame.style.styles

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import indi.midreamsheep.app.tre.shared.frame.style.ComposeStyle

class BackgroundColor(
    private val color: Color
):ComposeStyle{
    override fun decorate(modifier: Modifier): Modifier {
        return modifier.background(color)
    }
}

