package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.AnnotatedString
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext

abstract class TREStyleTextTree: TREStyleTextTreeInter {

    protected val children = mutableListOf<TREStyleTextTreeInter>()

    protected var selection = 0
    protected var isEdit = false
    protected var absoluteSelection = 0
    protected val styleTree:MutableState<AnnotatedString?> = mutableStateOf(null)


    private var parent: TREStyleTextTreeInter? = null

    override fun reset(selection: Int, absoluteSelection:Int, isEdit: Boolean) {
        this.selection = selection
        this.absoluteSelection = absoluteSelection
        this.isEdit = isEdit
        var select = selection
        for (child in children) {
            child.reset(selection,absoluteSelection, isEdit)
            select -= child.transformedSize()
        }
        this.styleTree.value = generateAnnotatedString()
    }

    override fun refresh() {
        this.styleTree.value = generateAnnotatedString()
        if(parent != null){
            parent!!.refresh()
        }
    }

    override fun getAnnotatedString() = styleTree

    override fun addChild(styleTextTree: TREStyleTextTreeInter) {
        children.add(styleTextTree)
        styleTextTree.setParent(this)
    }

    override fun addChildren(styleTextTrees: Array<TREStyleTextTreeInter>) {
        for (styleTextTree in styleTextTrees) {
            addChild(styleTextTree)
        }
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

    override fun remove() {}

    override fun insert() {}

    override fun resetPosition(position: Int): Int {
        var selection = position
        for (child in children) {
            if (selection>child.originalSize()){
                selection-=child.originalSize()
                continue
            }
            return child.resetPosition(selection)
        }
        return position
    }

    override fun check(position: Int):Boolean{
        var selection = position
        for (child in children) {
            if (selection>child.originalSize()){
                selection-=child.originalSize()
                continue
            }
            return child.check(selection)
        }
        return false
    }

    override fun keyEvent(key: KeyEvent, context: TREEditorWindowContext, position: Int):Boolean {
        //先托管给子类进行处理
        var selection = position
        for (child in children) {
            if (selection>child.originalSize()){
                selection-=child.originalSize()
                continue
            }
            val keyEvent = child.keyEvent(key, context, selection)
            if (keyEvent){
                return true
            }
        }
        return context.treTextFieldShortcutKeyManager.keyEvent(key,context)
    }
}