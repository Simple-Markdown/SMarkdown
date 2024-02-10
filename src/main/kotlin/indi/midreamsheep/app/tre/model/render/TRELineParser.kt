package indi.midreamsheep.app.tre.model.render

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.context.api.annotation.render.LineParser
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.render.parser.paragraph.DefaultParser
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.render.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRELineParser {

    @LineParser
    private val paragraphParser = HashMap<Char,List<ParagraphParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    fun parse(
        text: TextFieldValue,
        state: TRECoreLine,
        stateList: TREStateManager
    ): TRETextRender {
        if(text.text.isEmpty()) {
            val treCoreStyleTextRoot = TRECoreStyleTextRoot()
            treCoreStyleTextRoot.addChildren(TRECoreLeaf("", TREStyleTextOffsetMapping(0,0)))

            val render = TRETextRender(state)
            render.styleTextTree = treCoreStyleTextRoot
            return render
        }
        val startChar = text.text[0]
        var parser: ParagraphParser? = null;

        val parserList:MutableList<ParagraphParser> = mutableListOf()
        val paragraphParsers = paragraphParser[startChar]
        paragraphParsers?.forEach {
            if (it.formatCheck(text.text)){
                parserList.add(it)
            }
        }
        //找到权重最高的解析器
        var weight = 0
        parserList.forEach {
            val w = it.getWeight(text.text)
            if (w>weight){
                weight = w
                parser = it
            }
        }
        if (parser==null){
            parser = defaultParser
        }
        return parser!!.getAnnotatedString(text,stateList,state)
    }
}