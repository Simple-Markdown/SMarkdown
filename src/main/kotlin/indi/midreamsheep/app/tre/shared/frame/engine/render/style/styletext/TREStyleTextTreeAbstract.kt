package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext

abstract class TREStyleTextTreeAbstract: TREStyleTextTree {

    protected val children = mutableListOf<TREStyleTextTree>()

    protected var selection = -1
    protected var isEdit = true
    protected var absoluteSelection = -1
    protected val styleTree:MutableState<AnnotatedString> = mutableStateOf(buildAnnotatedString {  })


    private var parent: TREStyleTextTree? = null

    /**
     * 重设状态并判断是否需要对当前进行重新刷新
     * @return 如何不需要重刷就返回false，需要则返回true
     * */
    protected fun reset(selection: Int, absoluteSelection:Int, isEdit: Boolean):Boolean {
        val result = this.selection==selection&&this.absoluteSelection==absoluteSelection&&this.isEdit==isEdit
        this.selection = selection
        this.absoluteSelection = absoluteSelection
        this.isEdit = isEdit
        return !result
    }

    /**
     * 用于获取子类的所有AnnotatedString,封装了对子树的重设操作
     * */
    protected fun getChildrenAnnotatedStrings():List<AnnotatedString>{
        val results = mutableListOf<AnnotatedString>()
        var select = selection
        for (child in children) {
            results.add(child.getAnnotatedString(select,absoluteSelection,isEdit).value)
            select -= child.transformedSize()
        }
        return results
    }

    override fun refresh() {
        this.styleTree.value = generateAnnotatedString()
        if(parent != null){
            parent!!.refresh()
        }
    }

    override fun getAnnotatedString(selection: Int, absoluteSelection: Int, isEdit: Boolean): MutableState<AnnotatedString> {
        if (reset(selection,absoluteSelection,isEdit)){
            // 先刷新子树
            getChildrenAnnotatedStrings()
            styleTree.value = generateAnnotatedString()
        }
        return styleTree
    }

    override fun addChild(styleTextTree: TREStyleTextTree) {
        children.add(styleTextTree)
        styleTextTree.setParent(this)
    }

    override fun addChildren(styleTextTrees: Array<TREStyleTextTree>) {
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

    override fun getChildrenByOriginalOffset(offset: Int): TREStyleTextTree? {
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

    override fun getChildrenByTransformedOffset(offset: Int): TREStyleTextTree? {
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

    override fun setParent(parent: TREStyleTextTree) { this.parent = parent }



    override fun getChildTransformedRange(child: TREStyleTextTree) = object : TRERangeInter {
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

    override fun getChildOriginalRange(child: TREStyleTextTree) = object : TRERangeInter {
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
            return parent!!.getChildTransformedRange(this@TREStyleTextTreeAbstract).getStart()
        }
        override fun getEnd():Int{
            return getStart() + transformedSize()
        }
    }

    override fun getOriginalRange() = object : TRERangeInter {
        override fun getStart():Int{
            if (parent == null) return 0
            return parent!!.getChildOriginalRange(this@TREStyleTextTreeAbstract).getStart()
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

    override fun keyEvent(context: TREEditorContext, position: Int):Boolean {
        //先托管给子类进行处理
        var selection = position
        for (child in children) {
            if (selection>child.originalSize()){
                selection-=child.originalSize()
                continue
            }
            val keyEvent = child.keyEvent(context, selection)
            if (keyEvent){
                return true
            }
        }
        return false
    }
}