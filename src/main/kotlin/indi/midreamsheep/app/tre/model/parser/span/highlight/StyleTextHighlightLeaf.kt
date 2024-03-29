package indi.midreamsheep.app.tre.model.parser.span.highlight

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.model.render.style.styletext.root.TRECoreStyleTextRoot

class StyleTextHighlightLeaf(
    private val content: String,
    private val isDisplay: Boolean
): TRECoreStyleTextRoot() {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun build(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            append("(")
            if (isFocus&&isDisplay){
                withStyle(
                    style = SpanStyle(
                        background = Color.Blue.copy(alpha = 0.1f),
                    )
                ) {
                    for (child in getChildren()) {
                        append(child!!.build(isFocus))
                    }
                }
            }
            else {
                for (child in getChildren()) {
                    append(child!!.build(isFocus))
                }
            }
            append(")")
        }
    }

    override fun originalSize() = content.length + 2

    override fun transformedSize() = childrenTransformedSize() + 2

    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 1){
            return  offset
        }
        if (offset >= childrenOriginalSize() + 1){
            return offset + childrenTransformedSize() - childrenOriginalSize()
        }
        return super.originalToTransformed(offset-1)+1
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 1){
            return offset
        }
        if (offset >= childrenTransformedSize() + 1){
            return offset + childrenOriginalSize() - childrenTransformedSize()
        }
        return super.transformedToOriginal(offset-1)+1
    }
}