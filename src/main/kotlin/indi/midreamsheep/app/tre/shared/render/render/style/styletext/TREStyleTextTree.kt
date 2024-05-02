package indi.midreamsheep.app.tre.shared.render.render.style.styletext

import androidx.compose.ui.text.AnnotatedString

/**
 * 构建文本样式树
 * */
interface TREStyleTextTree {
    /**
     * 获取用于显示的AnnotatedString
     * */
    fun build(isFocus: Boolean):AnnotatedString
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
    fun addChildren(styleTextTree: TREStyleTextTree)
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
     * 当前节点的起始原文本偏移
     * */
    fun getOriginalStartOffset(): Int
    /**
     * 当前节点的结束原文本偏移
     * */
    fun getOriginalEndOffset(): Int
    /**
     * 当前节点的起始转换后文本偏移
     * */
    fun getTransformedStartOffset(): Int
    /**
     * 当前节点的结束转换后文本偏移
     * */
    fun getTransformedEndOffset(): Int
    /**
     * 获取父节点
     * */
    fun getParent(): TREStyleTextTree?
    /**
     * 设置父节点
     * */
    fun setParent(parent: TREStyleTextTree)
    /**
     * 获取字节点的起始原文本偏移
     * */
    fun getChildrenOriginalStartOffset(child: TREStyleTextTree): Int
    /**
     * 获取字节点的起始转换后文本偏移
     * */
    fun getChildrenTransformedStartOffset(child: TREStyleTextTree): Int

    /**
     * 获取子节点的原始起始偏移
     * */
    fun getChildrenOriginalStart(): Int

    /**
     * 获取子节点的转换后起始偏移
     * */
    fun getChildrenTransformedStart(): Int
}