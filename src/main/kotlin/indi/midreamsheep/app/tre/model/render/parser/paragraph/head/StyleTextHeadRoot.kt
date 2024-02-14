package indi.midreamsheep.app.tre.model.render.parser.paragraph.head

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot

class StyleTextHeadRoot(
    private val content: String,
    private val level: Int,
    private val isDisplay: Boolean,
): TRECoreStyleTextRoot() {

    override fun originalToTransformed(offset: Int): Int {
        if (isDisplay) return offset
        return offset - level -1
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay) return offset
        return offset + level + 1
    }

    override fun build(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (15+(6-level+1)*5).sp,
                )
            ) {
                if (isDisplay){
                    append("#".repeat(level) + " ")
                }
                append(content)
            }
        }
    }

    override fun originalSize(): Int {
        return content.length + level + 1
    }

    override fun transformedSize(): Int {
        return content.length+ if (isDisplay) level + 1 else 0
    }
}