package indi.midreamsheep.app.tre.model.render.parser.paragraph.list.unordered

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot

class StyleTextUnorderedListRoot(
    private val isDisplay: Boolean,
): TRECoreStyleTextRoot() {

    override fun originalToTransformed(offset: Int): Int {
        if (isDisplay){
            return offset
        }
        return super.originalToTransformed(offset-2)
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (isDisplay) return offset
        return super.transformedToOriginal(offset) + 2
    }

    override fun build(): AnnotatedString {
        return buildAnnotatedString {
            if (isDisplay){
                append("- ")
            }
            for (child in getChildren()) {
                append(child!!.build())
            }
        }
    }

    override fun originalSize(): Int {
        return super.originalSize() + 2
    }

    override fun transformedSize(): Int {
        return super.transformedSize()- if (isDisplay) 2 else 0
    }
}