package indi.midreamsheep.app.tre.shared.render.parser.span.italic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextItalicLeaf: TRECoreTreeRoot() {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontStyle = Italic
                )
            ) {
                for (child in getChildren()) {
                    append(child.getAnnotatedString().value)
                }
            }
        }
    }
}

class ItalicAffix: TRECoreTreeRoot(){
    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Gray
                )
            ) {
                if(isDisplay()){
                    append("*")
                }
            }
        }
    }

    private fun isDisplay(): Boolean {
        val originalRange = getParent()!!.getOriginalRange()
        return originalRange.getStart() <= absoluteSelection && absoluteSelection <= originalRange.getEnd()
    }

}