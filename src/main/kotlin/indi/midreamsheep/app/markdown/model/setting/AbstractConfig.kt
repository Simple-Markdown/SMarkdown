package indi.midreamsheep.app.markdown.model.setting

import androidx.compose.runtime.Composable

interface AbstractConfig {
    fun getComposable():@Composable ()->Unit
    fun getConfigName():String
    fun write():Pair<Boolean,String>
}