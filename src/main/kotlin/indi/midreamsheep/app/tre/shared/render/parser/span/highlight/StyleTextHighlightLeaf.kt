package indi.midreamsheep.app.tre.shared.render.parser.span.highlight

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextHighlightLeaf: TRECoreTreeRoot() {

    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            if (isHighlight()){
                withStyle(
                    style = SpanStyle(
                        background = Color.Blue.copy(alpha = 0.1f),
                    )
                ) {
                    for (child in getChildren()) {
                        append(child.generateAnnotatedString(isFocus))
                    }
                }
            }
            else {
                for (child in getChildren()) {
                    append(child.generateAnnotatedString(isFocus))
                }
            }
        }
    }

    private fun isHighlight(): Boolean {
        val originalRange = getOriginalRange()
        return absoluteSelection<=originalRange.getEnd()&&absoluteSelection>=originalRange.getStart()
    }
}