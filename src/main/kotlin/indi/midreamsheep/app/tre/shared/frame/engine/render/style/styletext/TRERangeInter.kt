package indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext

interface TRERangeInter{
    fun getStart():Int
    fun getEnd():Int
}

class TRERange(private val start: Int, private val end: Int): TRERangeInter {

    override fun getStart(): Int {
        return start
    }

    override fun getEnd(): Int {
        return end
    }
}