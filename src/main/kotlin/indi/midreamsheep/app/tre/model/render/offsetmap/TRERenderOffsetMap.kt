package indi.midreamsheep.app.tre.model.render.offsetmap

interface TRERenderOffsetMap {
    fun getStartOffset():Int
}

class TRERenderDefaultOffsetMap:TRERenderOffsetMap{
    override fun getStartOffset() = 0
}