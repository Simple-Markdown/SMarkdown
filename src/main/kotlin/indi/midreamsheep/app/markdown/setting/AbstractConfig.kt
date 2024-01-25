package indi.midreamsheep.app.markdown.setting

import androidx.compose.runtime.Composable

interface AbstractConfig {
    fun getComposable():@Composable ()->Unit
    fun getConfigName():String
    fun write():Pair<Boolean,String>
}