package indi.midreamsheep.app.tre.shared.frame.engine.parser.span

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREInlineParserManager {

    @InLineParserList
    private val metaParserMap = HashMap<String, List<indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser>>()

    private var init = false
    
    private val spanParser = HashMap<Char, MutableList<indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser>>()
    private val regParser = HashMap<String, indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser>()

    private fun init(){
        println(metaParserMap.size)
        for ((reg, line) in metaParserMap) {
            if(reg.startsWith("reg:")){
                regParser[reg.substring(4)] = line[0]
                continue
            }
            var startChar = reg[0]
            if(reg.startsWith("start:")){
                startChar = reg[6]
            }
            if(spanParser.containsKey(startChar)){
                spanParser[startChar]!!.addAll(line)
            }else{
                spanParser[startChar] = line.toMutableList()
            }
        }
        init=true
    }

    private fun check(){
        if(!init){
            init()
        }
    }

    fun getSpanParserMap(): HashMap<Char, MutableList<indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser>> {
        check()
        return spanParser
    }

    fun getRegParserMap():HashMap<String, indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser> {
        check()
        return regParser
    }
}