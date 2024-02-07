package indi.midreamsheep.app.tre.model.editor.parser.impl.span.bold

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.model.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.styletext.pojo.StyleTextOffsetMapping

class StyleTextBoldLeaf(
    private val content:String,
    private val offsetMapping: StyleTextOffsetMapping,
    private val isDisplay: Boolean
): TRECoreLeaf(content,offsetMapping) {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun build(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                if (isDisplay){
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray
                        )
                    ) {
                        append("**")
                    }
                }
                for (child in getChildren()) {
                    append(child!!.build())
                }
                if (isDisplay){
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray
                        )
                    ) {
                        append("**")
                    }
                }
            }
        }
    }

    override fun originalSize() = content.length + 4

    override fun transformedSize() = content.length + if (isDisplay) 4 else 0

    override fun originalToTransformed(offset: Int): Int {
        if (isDisplay) return super.originalToTransformed(offset)
        val off = offsetMapping.originalOffsetStart + offset - 2
        return if (off < 0) 0 else off
    }

    /**
     * 转换后文本到源文本的偏移
     * */
    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay) return super.transformedToOriginal(offset)
        return offsetMapping.transformedOffsetStart + offset + 2
    }
}