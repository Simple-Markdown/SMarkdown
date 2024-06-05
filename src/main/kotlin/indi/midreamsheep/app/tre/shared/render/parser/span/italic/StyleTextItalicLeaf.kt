package indi.midreamsheep.app.tre.shared.render.parser.span.italic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextItalicLeaf: TRECoreTreeRoot() {

    override fun generateAnnotatedString(): AnnotatedString {
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

    private var isDisplay = false

    override fun generateAnnotatedString(): AnnotatedString {
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


    fun isDisplay(): Boolean {
        val originalRange = getParent()!!.getOriginalRange()
        if(originalRange.getStart()<=absoluteSelection&&absoluteSelection<=originalRange.getEnd()&&isEdit){
            if(!isDisplay){
                isDisplay = true
                refresh()
            }
        }else{
            if(isDisplay){
                isDisplay = false
                refresh()
            }
        }
        return isDisplay
    }

    override fun transformedSize() = if(isDisplay) 1 else 0

    override fun originalSize() = 1

    override fun transformedToOriginal(offset: Int): Int {
        return if(isDisplay) offset else 0
    }

    override fun originalToTransformed(offset: Int): Int {
        return if(isDisplay) offset else 0
    }
}