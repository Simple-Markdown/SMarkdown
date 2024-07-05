package indi.midreamsheep.app.tre.shared.api.display

import androidx.compose.runtime.Composable

@FunctionalInterface
fun interface DisplayFunction:Display {

    @Composable
    fun show()

    override fun getComposable():@Composable ()->Unit = { show() }
}