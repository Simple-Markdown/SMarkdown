package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.AnnotatedString
import indi.midreamsheep.app.tre.shared.api.tre.TREClassId
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext

/**
 * 构建文本样式树
 * */
interface TREStyleTextTree:TREClassId {

    fun getTypeId() = getId()
    /**
     * 重新构建AnnotatedString
     * */
    fun refresh()
    /**
     * 用于构建AnnotatedString
     * */
    fun generateAnnotatedString():AnnotatedString
    /**
     * 获取用于显示的AnnotatedString
     * 当传入数据与原数据发生改变时会进行重新刷新，否则获取缓存AnnotatedString
     * @param selection 当前焦点位置(相对于当前标签的位置)
     * @param absoluteSelection 当前焦点位置(绝对位置)
     * @param isEdit 是否是编辑状态，预览状态下会强制显示所有的样式
     * */
    fun getAnnotatedString(selection: Int, absoluteSelection: Int, isEdit: Boolean): MutableState<AnnotatedString>

    /**
     * 源文本到转换后文本的偏移
     * */
    fun originalToTransformed(offset: Int): Int
    /**
     * 转换后文本到源文本的偏移
     * */
    fun transformedToOriginal(offset: Int): Int
    /**
     * 获取源文本长度
     * */
    fun originalSize():Int
    /**
     * 获取转换后文本长度
     * */
    fun transformedSize():Int
    /**
     * 添加子节点
     * */
    fun addChild(styleTextTree: TREStyleTextTree)
    /**
     * 添加子节点
     * */
    fun addChildren(styleTextTrees: Array<TREStyleTextTree>)
    /**
     * 获取子节点
     * */
    fun getChildren(): Array<TREStyleTextTree>
    /**
     * 通过源文本偏移获取子节点
     * */
    fun getChildrenByOriginalOffset(offset: Int): TREStyleTextTree?
    /**
     * 通过转换后文本偏移获取子节点
     * */
    fun getChildrenByTransformedOffset(offset: Int): TREStyleTextTree?
    /**
     * 获取父节点
     * */
    fun getParent(): TREStyleTextTree?
    /**
     * 设置父节点
     * */
    fun setParent(parent: TREStyleTextTree)
    /**
     * 获取原始文本偏移
     * */
    fun getOriginalRange(): TRERangeInter
    /**
     * 获取转换后文本偏移
     * */
    fun getTransformedRange(): TRERangeInter
    /**
     * 获取子节点的原始文本偏移
     * */
    fun getChildOriginalRange(child: TREStyleTextTree): TRERangeInter
    /**
     * 获取子节点的转换后文本偏移
     * */
    fun getChildTransformedRange(child: TREStyleTextTree): TRERangeInter
    /**
     * 当解析出时调用
     * */
    fun insert()
    /**
     * 当解析出时调用
     * */
    fun remove()
    /**
     * 对光标的相关约束
     * */
    fun check(position: Int):Boolean
    fun resetPosition(position: Int):Int

    fun keyEvent(context: TREEditorContext, position: Int): Boolean
}