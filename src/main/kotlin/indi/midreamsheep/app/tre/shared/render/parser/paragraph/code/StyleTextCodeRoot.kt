package indi.midreamsheep.app.tre.shared.render.parser.paragraph.code

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreStyleTextRoot

class StyleTextCodeRoot(
    private val content: String,
): TRECoreStyleTextRoot() {

    override fun originalToTransformed(offset: Int): Int {
        return offset
    }

    override fun transformedToOriginal(offset: Int): Int {
        return offset
    }

    override fun build(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.Gray
                )
            ) {
                append(content)
            }
        }
    }

    override fun originalSize(): Int {
        return content.length
    }

    override fun transformedSize(): Int {
        return content.length
    }
}