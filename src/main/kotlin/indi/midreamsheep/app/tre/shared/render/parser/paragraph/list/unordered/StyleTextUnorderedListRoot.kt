package indi.midreamsheep.app.tre.shared.render.parser.paragraph.list.unordered

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

class StyleTextUnorderedListRoot(
    private val isDisplay: Boolean,
): TRECoreTreeRoot() {

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

    override fun generateAnnotatedString(isFocus: Boolean): AnnotatedString {
        return buildAnnotatedString {
            if (isDisplay&&isFocus){
                append("- ")
            }
            for (child in getChildren()) {
                append(child.generateAnnotatedString(isFocus))
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