package indi.midreamsheep.app.tre.ui.app

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.api.Display

abstract class WindowDisplay :Display{
    var close: (() -> Unit)? = null
    @Composable
    abstract override fun display()
}