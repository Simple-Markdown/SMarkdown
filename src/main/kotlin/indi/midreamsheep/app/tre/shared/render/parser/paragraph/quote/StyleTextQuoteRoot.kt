package indi.midreamsheep.app.tre.shared.render.parser.paragraph.quote

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreStyleTextRoot

class StyleTextQuoteRoot(
    val level: Int,
): TRECoreStyleTextRoot() {


    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            for (child in getChildren()) {
                append(child.generateAnnotatedString(isFocus))
            }
        }
    }
}

class StyleTextQuotePrefix(
    val level: Int
): TRECoreStyleTextRoot() {

    private var isHidden = false

    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.Gray
                )
            ){
                append("> ".repeat(level))
            }
        }
    }

    override fun originalSize(): Int {
        return level*2
    }

    override fun transformedSize() = if(isDisplay()) level else 0
    private fun isDisplay(): Boolean {
        if (selection > level){
            isHidden = true
        }
        return (isHidden)||(!isEdit)
    }

}