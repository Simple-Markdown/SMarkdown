package indi.midreamsheep.app.tre.shared.render.parser.paragraph.head

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextHeadRoot(
    val level: Int,
): TRECoreTreeRoot() {

    override fun generateAnnotatedString(isFocus:Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (15+(6-level+1)*5).sp,
                )
            ) {
                for (child in getChildren()) {
                    append(child.getAnnotatedString().value)
                }
            }
        }
    }

    override fun originalSize(): Int {
        return childrenOriginalSize()
    }

    override fun transformedSize(): Int {
        return childrenTransformedSize()
    }

}

class StyleTextHeadPrefix(
    private val level: Int,
    private val render: TRERender,
): TRECoreTreeRoot() {

    private var isHidden = false

    override fun generateAnnotatedString(isFocus:Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (15+(6-level+1)*5).sp,
                    color = Color.Gray
                )
            ) {
                if (!isDisplay()) {
                    append("#".repeat(level) + " ")
                }
            }
        }
    }

    private fun isDisplay(): Boolean {
        if (selection > level  || !isEdit) {
            isHidden = true
            render.offsetMap = object : TRERenderOffsetMap() {
                override fun getStartOffset(): Int {
                    return level + 1
                }
            }
        }
        return isHidden
    }

    override fun transformedSize(): Int {
        return if (isHidden) 0 else level + 1
    }

    override fun originalSize(): Int {
        return level + 1
    }

    override fun transformedToOriginal(offset: Int): Int {
        return if (isHidden) 0 else offset
    }

    override fun originalToTransformed(offset: Int): Int {
        return if (isHidden) 0 else offset
    }
}