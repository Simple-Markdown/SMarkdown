package indi.midreamsheep.app.tre.model.styletext

abstract class TREStyleTextRoot:TREStyleTextTree {

    protected val children = mutableListOf<TREStyleTextTree>()

    override fun addChildren(styleTextTree: TREStyleTextTree) { children.add(styleTextTree) }

    override fun getChildren(): Array<TREStyleTextTree?> {
        val result = arrayOfNulls<TREStyleTextTree>(children.size)
        for (i in 0 until children.size) {
            children[i].also { result[i] = it }
        }
        return result
    }

    /**
     * 从源文本到转换后文本的偏移表
     * @param offset 相对于当前文本的偏移
     * */
    override fun originalToTransformed(offset: Int): Int {
        var point = offset
        for (child in children) {
            if (point <= child.originalSize()) {
                return child.originalToTransformed(point)
            }
            point -= child.originalSize()
        }
        return offset
    }

    /**
     * 从转换后文本到源文本的偏移表
     * @param offset 相对于当前文本的偏移
     * */
    override fun transformedToOriginal(offset: Int): Int {
        var point = offset
        for (child in children) {
            if (point <= child.transformedSize()) {
                return child.transformedToOriginal(point)
            }
            point -= child.transformedSize()
        }
        return offset
    }
}