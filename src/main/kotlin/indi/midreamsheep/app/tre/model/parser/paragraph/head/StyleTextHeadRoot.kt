package indi.midreamsheep.app.tre.model.parser.paragraph.head

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.model.render.style.styletext.root.TRECoreStyleTextRoot

class StyleTextHeadRoot(
    val level: Int,
    private val isDisplay: Boolean,
): TRECoreStyleTextRoot() {

    override fun originalToTransformed(offset: Int): Int {
        if (isDisplay) return offset
        return offset - level - 1
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay) return offset
        return offset + level+1
    }

    override fun build(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (15+(6-level+1)*5).sp,
                )
            ) {
                withStyle(
                    SpanStyle(
                        color = Color.Gray
                    )
                ){
                    if (isDisplay&&isFocus){
                        append("#".repeat(level) + " ")
                    }
                }
                for (child in getChildren()) {
                    append(child.build(isFocus))
                }
            }
        }
    }

    override fun originalSize(): Int {
        return childrenOriginalSize() + level + 1
    }

    override fun transformedSize(): Int {
        return childrenTransformedSize() + if (isDisplay) level + 1 else 0
    }
}