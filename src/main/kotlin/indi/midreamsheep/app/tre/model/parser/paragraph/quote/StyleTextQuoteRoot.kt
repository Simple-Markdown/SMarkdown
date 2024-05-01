package indi.midreamsheep.app.tre.model.parser.paragraph.quote

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.style.styletext.root.TRECoreStyleTextRoot

class StyleTextQuoteRoot(
    val level: Int,
    private val isDisplay: Boolean,
): TRECoreStyleTextRoot() {

    override fun originalToTransformed(offset: Int): Int {
        if (isDisplay){
            return offset
        }
        return super.originalToTransformed(offset-level*2)
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay) return offset
        return super.transformedToOriginal(offset) + level*2
    }

    override fun build(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            if (isFocus&&isDisplay){
                withStyle(
                    SpanStyle(
                        color = Color.Gray
                    )
                ){
                    append("> ".repeat(level))
                }
            }
            for (child in getChildren()) {
                append(child.build(isFocus))
            }
        }
    }

    override fun originalSize(): Int {
        return super.originalSize() + level*2
    }

    override fun transformedSize(): Int {
        return super.transformedSize()- if (isDisplay) level *2 else 0
    }

    override fun getChildrenOriginalStart() = level*2

    override fun getChildrenTransformedStart() = if (isDisplay) level*2 else 0
}