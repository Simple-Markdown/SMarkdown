package indi.midreamsheep.app.tre.model.render.parser.span.link

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot

class StyleTextLinkLeaf(
    private val displayName:String,
    private val linkName:String,
    private val isDisplay: Boolean
): TRECoreStyleTextRoot() {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun build(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            if (isDisplay){
                withStyle(
                   SpanStyle(
                       color = Color.Gray
                   )
                ){
                    append("(")
                }
            }
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = Color.Blue
                )
            ) {
                append(displayName)
            }
            if (isDisplay){
                withStyle(
                    SpanStyle(
                        color = Color.Gray
                    )
                ){
                    append(")")
                    append("[")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color.Blue
                        )
                    ) {
                        append(linkName)
                    }
                    append("]")
                }
            }
        }
    }

    override fun originalSize() = linkName.length + displayName.length + 4

    override fun transformedSize() = displayName.length + if (isDisplay) linkName.length + 4 else 0

    override fun originalToTransformed(offset: Int): Int {
        return offset
    }

    /**
     * 转换后文本到源文本的偏移
     *
     * */
    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay){
            return offset
        }
        return offset
    }
}