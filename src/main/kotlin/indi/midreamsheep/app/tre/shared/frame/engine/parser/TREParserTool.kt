package indi.midreamsheep.app.tre.shared.frame.engine.parser

class RegPointTable{
    private val regPointTable = HashMap<Int,MutableList<TREInlineStyleParser>>()

    fun add(text:String, reg:String, parser: TREInlineStyleParser){
        reg.toRegex().findAll(text).map { it.range.first }.toList().forEach {
            if (regPointTable.containsKey(it)){
                regPointTable[it]!!.add(parser)
            }else{
                regPointTable[it] = mutableListOf(parser)
            }
        }
    }

    fun get(point:Int) : List<TREInlineStyleParser> {
        return if (regPointTable.containsKey(point)){
            regPointTable[point]!!
        }else{
            listOf()
        }
    }
}