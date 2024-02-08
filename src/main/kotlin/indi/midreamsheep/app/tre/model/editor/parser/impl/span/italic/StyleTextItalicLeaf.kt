package indi.midreamsheep.app.tre.model.editor.parser.impl.span.italic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.model.styletext.pojo.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.styletext.root.TRECoreStyleTextRoot

class StyleTextItalicLeaf(
    private val content:String,
    private val offsetMapping: TREStyleTextOffsetMapping,
    private val isDisplay: Boolean
): TRECoreStyleTextRoot() {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun build(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontStyle = Italic
                )
            ) {
                if (isDisplay){
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray
                        )
                    ) {
                        append("*")
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
                        append("*")
                    }
                }
            }
        }
    }

    override fun originalSize() = childrenOriginalSize() + 2

    override fun transformedSize() = childrenTransformedSize() + if (isDisplay) 2 else 0

    override fun originalToTransformed(offset: Int): Int {
        if(isDisplay){
            if (offset <= 1){
                return offsetMapping.transformedOffsetStart + offset
            }
            if (offset >= childrenOriginalSize() + 1){
                return offsetMapping.transformedOffsetStart + offset+ childrenTransformedSize() - childrenOriginalSize()
            }
            return super.originalToTransformed(offset - 1)
        }
        return super.originalToTransformed(offset-1)
    }

    /**
     * 转换后文本到源文本的偏移
     *
     * */
    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay){
            return super.transformedToOriginal(offset-1)
        }
        return super.transformedToOriginal(offset)
    }
}