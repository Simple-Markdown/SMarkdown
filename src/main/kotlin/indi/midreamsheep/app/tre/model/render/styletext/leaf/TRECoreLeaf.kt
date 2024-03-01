package indi.midreamsheep.app.tre.model.render.styletext.leaf

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot

/**
 * 核心叶子节点，内部存储纯文本内容
 * */
open class TRECoreLeaf(private val content:String): TRECoreStyleTextRoot() {
    override fun originalSize() = content.length
    override fun transformedSize() = content.length

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun build(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            append(content)
        }
    }

    /**
     * 源文本到转换后文本的偏移
     * */
    override fun originalToTransformed(offset: Int): Int = offset

    /**
     * 转换后文本到源文本的偏移
     * */
    override fun transformedToOriginal(offset: Int): Int = offset
}