package indi.midreamsheep.app.tre.model.render.style.styletext

import androidx.compose.ui.text.AnnotatedString

/**
 * 构建文本样式树
 * */
interface TREStyleTextTree {
    /**
     * 获取用于显示的AnnotatedString
     * */
    fun build(isFocus: Boolean):AnnotatedString
    /**
     * 源文本到转换后文本的偏移
     * */
    fun originalToTransformed(offset: Int): Int
    /**
     * 转换后文本到源文本的偏移
     * */
    fun transformedToOriginal(offset: Int): Int
    fun originalSize():Int
    fun transformedSize():Int

    fun addChildren(styleTextTree: TREStyleTextTree)
    fun getChildren(): Array<TREStyleTextTree?>
}