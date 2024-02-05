package indi.midreamsheep.app.tre.api

import androidx.compose.runtime.Composable

fun interface Display{
    object None : Display { @Composable override fun display() {} }
    @Composable
    fun display()
}