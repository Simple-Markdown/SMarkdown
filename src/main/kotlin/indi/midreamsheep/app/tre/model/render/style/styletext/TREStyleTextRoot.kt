package indi.midreamsheep.app.tre.model.render.style.styletext

abstract class TREStyleTextRoot: TREStyleTextTree {

    protected val children = mutableListOf<TREStyleTextTree>()

    private var parent:TREStyleTextTree? = null

    override fun addChildren(styleTextTree: TREStyleTextTree) {
        children.add(styleTextTree)
        styleTextTree.setParent(this)
    }

    override fun getChildren() = children.toTypedArray()

    /**
     * 从源文本到转换后文本的偏移表
     * @param offset 相对于当前文本的偏移
     * */
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

    /**
     * 从转换后文本到源文本的偏移表
     * @param offset 相对于当前文本的偏移
     * */
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

    /**
     * 通过源文本偏移获取子节点
     * */
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

    /**
     * 通过转换后文本偏移获取子节点
     * */
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

    override fun getOriginalStartOffset(): Int {
        if (parent == null) return 0
        return parent!!.getChildrenOriginalStartOffset(this)
    }

    override fun getOriginalEndOffset() = getOriginalStartOffset() + originalSize()

    override fun getTransformedStartOffset(): Int {
        if (parent == null) return 0
        return parent!!.getChildrenTransformedStartOffset(this)
    }

    override fun getTransformedEndOffset() = getTransformedStartOffset() + transformedSize()

    override fun getParent() = parent

    override fun setParent(parent: TREStyleTextTree) { this.parent = parent }

    override fun getChildrenOriginalStartOffset(child: TREStyleTextTree): Int {
        var offset = getChildrenOriginalStart()
        for (c in children) {
            if (c == child) return offset
            offset += c.originalSize()
        }
        return offset
    }

    override fun getChildrenTransformedStartOffset(child: TREStyleTextTree): Int {
        var offset = getTransformedStartOffset()
        for (c in children) {
            if (c == child) return offset
            offset += c.transformedSize()
        }
        return offset
    }

    override fun getChildrenOriginalStart() = 0

    override fun getChildrenTransformedStart() = 0
}