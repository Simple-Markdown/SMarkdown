package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRELineParserManager{

    private var init = false

    @LineParserMap
    private val parser = HashMap<String,List<indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser>>()

    private val paragraphParser = HashMap<Char,MutableList<indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser>>()

    private val paragraphRegParser = HashMap<String,List<indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    fun get(text: String, blockManager: TREBlockManager, lineNumber: Int): indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser {
        if(!init) init()

        val startChar = text[0]
        var parser: indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser? = null;
        val parserList:MutableList<indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser> = mutableListOf()
        val paragraphParsers = paragraphParser[startChar]
        paragraphRegParser.forEach {
            if (it.key.toRegex().matches(text)){
                it.value.forEach { parser ->
                    parserList.add(parser)
                }
            }
        }
        paragraphParsers?.forEach {
            if (it.formatCheck(text, blockManager,lineNumber)){
                parserList.add(it)
            }
        }
        //找到权重最高的解析器
        var weight = 0
        parserList.forEach {
            val w = it.getWeight(text)
            if (w>weight){
                weight = w
                parser = it
            }
        }
        if (parser==null){
            parser = defaultParser
        }
        return parser!!
    }


    private fun init(){
        for ((reg, line) in parser) {
            if(reg.startsWith("reg:")){
                paragraphRegParser[reg.substring(4)] = line
                continue
            }
            var startChar = reg[0]
            if(reg.startsWith("start:")){
                startChar = reg[6]
            }
            if(paragraphParser.containsKey(startChar)){
                paragraphParser[startChar]!!.addAll(line)
            }else{
                paragraphParser[startChar] = line.toMutableList()
            }
        }
        init = true
    }
}