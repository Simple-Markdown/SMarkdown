package indi.midreamsheep.app.tre.api

import androidx.compose.runtime.Composable

fun interface TREComposable {
    fun getComposable(): @Composable ()->Unit
}