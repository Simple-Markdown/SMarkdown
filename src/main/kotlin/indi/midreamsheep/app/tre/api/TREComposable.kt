package indi.midreamsheep.app.tre.api

import androidx.compose.runtime.Composable

@FunctionalInterface
fun interface TREComposable {
    fun getComposable(): @Composable ()->Unit

    companion object {
        var EMPTY: TREComposable = TREComposable { {} }
    }
}