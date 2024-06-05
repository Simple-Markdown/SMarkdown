package indi.midreamsheep.app.tre.shared.render.parser

class RegPointTable{
    private val regPointTable = HashMap<Int,MutableList<InlineParser>>()

    fun add(text:String, reg:String, parser:InlineParser){
        reg.toRegex().findAll(text).map { it.range.first }.toList().forEach {
            if (regPointTable.containsKey(it)){
                regPointTable[it]!!.add(parser)
            }else{
                regPointTable[it] = mutableListOf(parser)
            }
        }
    }

    fun get(point:Int) : List<InlineParser> {
        return if (regPointTable.containsKey(point)){
            regPointTable[point]!!
        }else{
            listOf()
        }
    }
}