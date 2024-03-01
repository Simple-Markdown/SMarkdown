package indi.midreamsheep.app.tre.model.render

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParser
import indi.midreamsheep.app.tre.api.annotation.render.line.LineRegParser
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.render.parser.paragraph.DefaultParser
import indi.midreamsheep.app.tre.model.render.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRELineParser {

    @LineParser
    private val paragraphParser = HashMap<Char,List<ParagraphParser>>()

    @LineRegParser
    private val paragraphRegParser = HashMap<String,List<ParagraphParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    fun parse(
        text: String,
        selection: Int,
        state: TRECoreLine,
        stateList: TREStateManager
    ): TRETextRender {

        if(text.isEmpty()) {
            val treCoreStyleTextRoot = TRECoreStyleTextRoot()
            treCoreStyleTextRoot.addChildren(TRECoreLeaf(""))

            val render = TRETextRender(state)
            render.styleTextTree = treCoreStyleTextRoot
            return render
        }
        val startChar = text[0]
        var parser: ParagraphParser? = null;

        val parserList:MutableList<ParagraphParser> = mutableListOf()
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
        return parser!!.getAnnotatedString(text,selection,stateList,state)
    }
}