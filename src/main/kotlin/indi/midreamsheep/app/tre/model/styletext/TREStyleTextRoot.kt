package indi.midreamsheep.app.tre.model.styletext

abstract class TREStyleTextRoot:StyleTextTree {

    protected val children = mutableListOf<StyleTextTree>()

    override fun addChildren(styleTextTree: StyleTextTree) { children.add(styleTextTree) }

    override fun getChildren(): Array<StyleTextTree?> {
        val result = arrayOfNulls<StyleTextTree>(children.size)
        for (i in 0 until children.size) {
            children[i].also { result[i] = it }
        }
        return result
    }

    override fun originalToTransformed(offset: Int): Int {
        var point = offset
        for (child in children) {
            if (point < child.originalSize()) {
                return child.originalToTransformed(point)
            }
            point -= child.originalSize()
        }
        return offset
    }

    override fun transformedToOriginal(offset: Int): Int {
        var point = offset
        for (child in children) {
            if (point < child.transformedSize()) {
                return child.transformedToOriginal(point)
            }
            point -= child.transformedSize()
        }
        return offset
    }
}