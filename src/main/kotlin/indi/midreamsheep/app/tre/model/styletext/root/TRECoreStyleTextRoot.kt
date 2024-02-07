package indi.midreamsheep.app.tre.model.styletext.root

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextRoot

open class TRECoreStyleTextRoot: TREStyleTextRoot() {

    override fun build(): AnnotatedString {
        return buildAnnotatedString {
            children.forEach {
                append(it.build())
            }
        }
    }

    override fun originalSize(): Int {
        var size = 0
        children.forEach {
            size += it.originalSize()
        }
        return size
    }

    override fun transformedSize(): Int {
        var size = 0
        children.forEach {
            size += it.transformedSize()
        }
        return size
    }

}