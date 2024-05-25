package indi.midreamsheep.app.tre.shared.render.render.style.styletext

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString

abstract class TREStyleTextTree: TREStyleTextTreeInter {

    protected val children = mutableListOf<TREStyleTextTreeInter>()

    protected var selection = 0
    protected var isEdit = false
    protected val styleTree:MutableState<AnnotatedString?> = mutableStateOf(null)

    private var parent: TREStyleTextTreeInter? = null

    override fun setState(selection: Int, isEdit: Boolean) {
        this.selection = selection
        this.isEdit = isEdit
        var select = selection
        for (child in children) {
            child.setState(selection, isEdit)
            select -= child.transformedSize()
        }
        this.styleTree.value = generateAnnotatedString(isEdit)
    }

    /**
     * 获取用于显示的AnnotatedString
     * */
    override fun getAnnotatedString() = styleTree

    override fun addChildren(styleTextTree: TREStyleTextTreeInter) {
        children.add(styleTextTree)
        styleTextTree.setParent(this)
    }

    override fun getChildren() = children.toTypedArray()

    override fun originalToTransformed(offset: Int): Int {
        var point = offset
        var transformedOffset = 0
        for (child in children) {
            if (point <= child.originalSize()) {
                return child.originalToTransformed(point)+transformedOffset
            }
            point -= child.originalSize()
            transformedOffset += child.transformedSize()
        }
        return transformedOffset
    }

    override fun transformedToOriginal(offset: Int): Int {
        var point = offset
        var originalOffset = 0
        for (child in children) {
            if (point <= child.transformedSize()) {
                return child.transformedToOriginal(point)+originalOffset
            }
            point -= child.transformedSize()
            originalOffset += child.originalSize()
        }
        return offset
    }

    override fun getChildrenByOriginalOffset(offset: Int): TREStyleTextTreeInter? {
        if (children.isEmpty()) return this
        var point = offset
        for (child in children) {
            if (point <= child.originalSize()) {
                return child.getChildrenByOriginalOffset(point)
            }
            point -= child.originalSize()
        }
        return this
    }

    override fun getChildrenByTransformedOffset(offset: Int): TREStyleTextTreeInter? {
        if (children.isEmpty()) return this
        var point = offset
        for (child in children) {
            if (point <= child.transformedSize()) {
                return child.getChildrenByTransformedOffset(point)
            }
            point -= child.transformedSize()
        }
        return this
    }
    override fun getParent() = parent

    override fun setParent(parent: TREStyleTextTreeInter) { this.parent = parent }



    override fun getChildTransformedRange(child: TREStyleTextTreeInter) = object : TRERangeInter {
        override fun getStart():Int{
            var offset = getTransformedRange().getStart()
            for (c in children) {
                if (c == child) return offset
                offset += c.transformedSize()
            }
            return offset
        }
        override fun getEnd():Int{
            var offset = getTransformedRange().getEnd()
            for (c in children) {
                offset += c.transformedSize()
                if (c == child) return offset
            }
            return offset
        }
    }

    override fun getChildOriginalRange(child: TREStyleTextTreeInter) = object : TRERangeInter {
        override fun getStart():Int{
            var offset = getOriginalRange().getStart()
            for (c in children) {
                if (c == child) return offset
                offset += c.originalSize()
            }
            return offset
        }
        override fun getEnd():Int{
            var offset = getOriginalRange().getEnd()
            for (c in children) {
                offset += c.originalSize()
                if (c == child) return offset
            }
            return offset
        }
    }

    override fun getTransformedRange()= object : TRERangeInter {
        override fun getStart():Int{
            if (parent == null) return 0
            return parent!!.getChildTransformedRange(this@TREStyleTextTree).getStart()
        }
        override fun getEnd():Int{
            return getStart() + transformedSize()
        }
    }

    override fun getOriginalRange() = object : TRERangeInter {
        override fun getStart():Int{
            if (parent == null) return 0
            return parent!!.getChildOriginalRange(this@TREStyleTextTree).getStart()
        }
        override fun getEnd():Int{
            return getStart() + originalSize()
        }
    }
}