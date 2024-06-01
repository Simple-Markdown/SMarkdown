package indi.midreamsheep.app.tre.shared.render.parser.paragraph.quote

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextQuoteRoot(
    val level: Int,
): TRECoreTreeRoot() {

    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            for (child in getChildren()) {
                append(child.generateAnnotatedString(isFocus))
            }
        }
    }
}

class StyleTextQuotePrefix(
    private val level: Int,
    private val render: TRERender,
): TRECoreTreeRoot() {

    private var isHidden = false

    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.Gray
                )
            ){
                if(isDisplay()){
                    append("> ".repeat(level))
                }
            }
        }
    }

    override fun originalSize(): Int {
        return level*2
    }

    override fun transformedSize() = if(isDisplay()) level else 0
    private fun isDisplay(): Boolean {
        if (selection > level || !isEdit){
            isHidden = true
            render.offsetMap = object : TRERenderOffsetMap() {
                override fun getStartOffset(): Int {
                    return level*2
                }
            }
        }
        return !isHidden
    }

    override fun transformedToOriginal(offset: Int) = if(!isHidden) offset else 0

    override fun originalToTransformed(offset: Int) = if(!isHidden) offset else 0
}