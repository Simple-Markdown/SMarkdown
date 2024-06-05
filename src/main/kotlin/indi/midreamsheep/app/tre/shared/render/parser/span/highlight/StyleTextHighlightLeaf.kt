package indi.midreamsheep.app.tre.shared.render.parser.span.highlight

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextHighlightLeaf: TRECoreTreeRoot() {

    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            if (isHighlight()){
                withStyle(
                    style = SpanStyle(
                        background = Color.Blue.copy(alpha = 0.1f),
                    )
                ) {
                    for (child in getChildren()) {
                        append(child.generateAnnotatedString())
                    }
                }
            }
            else {
                for (child in getChildren()) {
                    append(child.generateAnnotatedString())
                }
            }
        }
    }

    private fun isHighlight(): Boolean {
        val originalRange = getOriginalRange()
        return absoluteSelection<=originalRange.getEnd()&&absoluteSelection>=originalRange.getStart()
    }
}