package indi.midreamsheep.app.tre.model.setting

import androidx.compose.runtime.Composable

interface AbstractConfig {
    fun getComposable():@Composable ()->Unit
    fun getConfigName():String
    fun write():Pair<Boolean,String>
}