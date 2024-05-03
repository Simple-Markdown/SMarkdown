package indi.midreamsheep.app.tre.shared.render.parser.paragraph

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.api.annotation.render.line.LineRegParser
import indi.midreamsheep.app.tre.api.inter.manager.TREListManager
import indi.midreamsheep.app.tre.shared.render.parser.LineParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRELineParserManager : TREListManager<LineParser>{

    @LineParserMap
    private val paragraphParser = HashMap<Char,List<LineParser>>()

    @LineRegParser
    private val paragraphRegParser = HashMap<String,List<LineParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    override fun get(text: String): LineParser {
        val startChar = text[0]
        var parser: LineParser? = null;
        val parserList:MutableList<LineParser> = mutableListOf()
        val paragraphParsers = paragraphParser[startChar]
        paragraphRegParser.forEach {
            if (it.key.toRegex().matches(text)){
                it.value.forEach { parser ->
                    parserList.add(parser)
                }
            }
        }
        paragraphParsers?.forEach {
            if (it.formatCheck(text)){
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

}