package indi.midreamsheep.app.tre.shared.api.display

import androidx.compose.runtime.Composable

/**
 * 对ui层的封装
 * 用于分离ui层和逻辑层
 * */
fun interface Display{
    companion object{
        val None = Display { {} }
    }
    fun getComposable():@Composable ()->Unit
}