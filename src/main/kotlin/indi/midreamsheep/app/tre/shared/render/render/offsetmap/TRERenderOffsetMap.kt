package indi.midreamsheep.app.tre.shared.render.render.offsetmap

open class TRERenderOffsetMap {
    open fun check(offset:Int) = offset < getStartOffset()
    open fun resetOffset(offset: Int) = getStartOffset()
    open fun getStartOffset():Int = 0
    open fun getEndOffset():Int = -1
}