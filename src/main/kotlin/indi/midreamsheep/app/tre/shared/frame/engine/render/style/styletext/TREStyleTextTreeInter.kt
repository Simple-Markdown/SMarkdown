package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext

import androidx.compose.runtime.MutableState
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.AnnotatedString
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.shared.api.tre.TREClassId

/**
 * 构建文本样式树
 * */
interface TREStyleTextTreeInter:TREClassId {

    fun getTypeId() = getId()
    /**
     * 设置状态,进行相关操作时需要设置状态用于刷新
     * 例如:文本焦点改变时,需要设置焦点状态
     * @param selection 当前焦点位置(相对于当前标签的位置)
     * @param absoluteSelection 当前焦点位置(绝对位置)
     * @param isEdit 是否是编辑状态，预览状态下会强制显示所有的样式
     * */
    fun reset(selection: Int,absoluteSelection:Int, isEdit: Boolean)
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
     * */
    fun getAnnotatedString(): MutableState<AnnotatedString?>
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
    fun addChild(styleTextTree: TREStyleTextTreeInter)
    /**
     * 添加子节点
     * */
    fun addChildren(styleTextTrees: Array<TREStyleTextTreeInter>)
    /**
     * 获取子节点
     * */
    fun getChildren(): Array<TREStyleTextTreeInter>
    /**
     * 通过源文本偏移获取子节点
     * */
    fun getChildrenByOriginalOffset(offset: Int): TREStyleTextTreeInter?
    /**
     * 通过转换后文本偏移获取子节点
     * */
    fun getChildrenByTransformedOffset(offset: Int): TREStyleTextTreeInter?
    /**
     * 获取父节点
     * */
    fun getParent(): TREStyleTextTreeInter?
    /**
     * 设置父节点
     * */
    fun setParent(parent: TREStyleTextTreeInter)
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
    fun getChildOriginalRange(child: TREStyleTextTreeInter): TRERangeInter
    /**
     * 获取子节点的转换后文本偏移
     * */
    fun getChildTransformedRange(child: TREStyleTextTreeInter): TRERangeInter
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

    //将特定快捷键监听交由styleTree处理
    fun keyEvent(key: KeyEvent, context: TREEditorWindowContext, position: Int): Boolean
}