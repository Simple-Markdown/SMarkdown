package indi.midreamsheep.app.tre.frame.style

import androidx.compose.ui.Modifier

abstract class ComponentStyle {
    abstract fun setStyle(modifier: Modifier): Modifier
}

fun Modifier.style(
    style: ComponentStyle
): Modifier {
    return style.setStyle(this)
}
