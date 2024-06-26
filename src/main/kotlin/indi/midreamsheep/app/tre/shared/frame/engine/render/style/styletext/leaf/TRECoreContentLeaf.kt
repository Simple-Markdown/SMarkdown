package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

/**
 * 核心叶子节点，内部存储纯文本内容
 * */
open class TRECoreContentLeaf(private val content:String): TRECoreTreeRoot() {
    override fun originalSize() = content.length
    override fun transformedSize() = content.length

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun generateAnnotatedString(): AnnotatedString {
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