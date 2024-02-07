package indi.midreamsheep.app.tre.model.editor.parser.impl.paragraph.head

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.model.styletext.root.TRECoreStyleTextRoot

class StyleTextHeadRoot(
    private val content: String,
    private val level: Int,
    private val isDisplay:Boolean
): TRECoreStyleTextRoot() {

    override fun originalToTransformed(offset: Int): Int {
        if (isDisplay) return offset
        if (offset - level -1 < 0) return 0
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
                    fontSize = (10+level*10).sp
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
        return super.originalSize() + level + 1
    }

    override fun transformedSize(): Int {
        return super.transformedSize()+ if (isDisplay) level + 1 else 0
    }
}