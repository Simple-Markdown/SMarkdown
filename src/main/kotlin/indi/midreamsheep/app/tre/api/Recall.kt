package indi.midreamsheep.app.tre.api

import androidx.compose.runtime.Composable

interface Recall<T> {
    @Composable
    fun recall(value:T)
}