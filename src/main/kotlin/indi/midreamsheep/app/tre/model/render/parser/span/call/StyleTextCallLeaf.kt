package indi.midreamsheep.app.tre.model.render.parser.span.call

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot

class StyleTextCallLeaf(
    private val offsetMapping: TREStyleTextOffsetMapping,
    private val isDisplay: Boolean,
    private val id: Long
): TRECoreStyleTextRoot() {

    override fun build(): AnnotatedString {
        return buildAnnotatedString {
            if (isDisplay){
                appendInlineContent(id.toString())
                return@buildAnnotatedString
            }
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                )
            ) {
                append("Call")
            }
        }
    }

    /**
     * 源文本大小为原文本长度+4(四个*的长度)
     * */
    override fun originalSize() = 4

    /**
     * 转换后文本大小为转换后文本长度+4(四个*的长度，当显示时才加上4)
     * */
    override fun transformedSize() = 4 + if (isDisplay) 1 else 0

    override fun originalToTransformed(offset: Int): Int {
        return offsetMapping.transformedOffsetStart + offset
    }

    override fun transformedToOriginal(offset: Int): Int {
        return offsetMapping.originalOffsetStart + offset
    }
}