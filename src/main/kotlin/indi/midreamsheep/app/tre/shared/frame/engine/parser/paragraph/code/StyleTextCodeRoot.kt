package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.code

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

class StyleTextCodeRoot(
    private val content: String,
): TRECoreTreeRoot() {

    override fun originalToTransformed(offset: Int): Int {
        return offset
    }

    override fun transformedToOriginal(offset: Int): Int {
        return offset
    }

    override fun generateAnnotatedString(): AnnotatedString {
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