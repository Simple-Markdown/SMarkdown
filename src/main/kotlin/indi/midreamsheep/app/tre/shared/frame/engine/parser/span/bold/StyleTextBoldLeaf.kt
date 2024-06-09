package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.bold

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

class StyleTextBoldLeaf: TRECoreTreeRoot() {

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                for (child in getChildren()) {
                    append(child.getAnnotatedString().value)
                }
            }
        }
    }
}

class StyleTextBoldAffix: TRECoreTreeRoot() {

    private var isDisplay = false

    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            if(isDisplay()){
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray
                    )
                ) {
                    append("**")
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

    override fun transformedSize() = if(isDisplay) 2 else 0

    override fun originalSize() = 2

    override fun transformedToOriginal(offset: Int): Int {
        return if(isDisplay) offset else 0
    }

    override fun originalToTransformed(offset: Int): Int {
        return if(isDisplay) offset else 0
    }
}