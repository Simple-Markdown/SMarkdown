package indi.midreamsheep.app.tre.api

import androidx.compose.runtime.Composable

/**
 * 对ui层的封装
 * 用于分离ui层和逻辑层
 * */
fun interface Display{
    object None : Display { @Composable override fun display() {} }
    @Composable
    fun display()
}