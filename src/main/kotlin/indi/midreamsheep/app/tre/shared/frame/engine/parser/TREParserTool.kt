package indi.midreamsheep.app.tre.shared.frame.engine.parser

class RegPointTable{
    private val regPointTable = HashMap<Int,MutableList<indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser>>()

    fun add(text:String, reg:String, parser: indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser){
        reg.toRegex().findAll(text).map { it.range.first }.toList().forEach {
            if (regPointTable.containsKey(it)){
                regPointTable[it]!!.add(parser)
            }else{
                regPointTable[it] = mutableListOf(parser)
            }
        }
    }

    fun get(point:Int) : List<indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser> {
        return if (regPointTable.containsKey(point)){
            regPointTable[point]!!
        }else{
            listOf()
        }
    }
}