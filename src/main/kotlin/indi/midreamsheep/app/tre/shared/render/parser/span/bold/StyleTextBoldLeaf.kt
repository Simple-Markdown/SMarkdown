package indi.midreamsheep.app.tre.shared.render.parser.span.bold

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreStyleTextRoot

class StyleTextBoldLeaf(
    private val content:String,
    private val isDisplay: Boolean
): TRECoreStyleTextRoot() {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                if (isFocus&&isDisplay){
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray
                        )
                    ) {
                        append("**")
                    }
                }
                for (child in getChildren()) {
                    append(child.generateAnnotatedString(isFocus))
                }
                if (isFocus&&isDisplay){
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

    /**
     * 源文本大小为原文本长度+4(四个*的长度)
     * */
    override fun originalSize() = content.length + 4

    /**
     * 转换后文本大小为转换后文本长度+4(四个*的长度，当显示时才加上4)
     * */
    override fun transformedSize() = childrenTransformedSize() + if (isDisplay) 4 else 0

    /**
     * 偏移表
     * **content**
     * 8
     * **Ren**
     * */
    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 2){
            return offset
        }
        if (offset >= childrenOriginalSize() + 2){
            return offset + childrenTransformedSize() - childrenOriginalSize()
        }
        return super.originalToTransformed(offset-2) + 2
    }

    /**
     * 转换后文本到源文本的偏移
     * **content**
     * content
     * */
    override fun transformedToOriginal(offset: Int): Int {
        return super.transformedToOriginal(offset-2)+2
    }
}