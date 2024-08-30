package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeAbstract

open class TRECoreTreeRoot: TREStyleTextTreeAbstract() {

    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            children.forEach {
                append(it.generateAnnotatedString())
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

    protected fun childrenOriginalSize(): Int {
        var size = 0
        children.forEach {
            size += it.originalSize()
        }
        return size
    }

    protected fun childrenTransformedSize(): Int {
        var size = 0
        children.forEach {
            size += it.transformedSize()
        }
        return size
    }

}