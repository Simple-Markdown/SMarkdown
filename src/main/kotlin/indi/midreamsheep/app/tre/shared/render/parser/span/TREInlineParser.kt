package indi.midreamsheep.app.tre.shared.render.parser.span

import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.parser.RegPointTable
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreContentLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TREInlineParser {

    @Injector
    private var treInlineParserManager: TREInlineParserManager? = null

    fun parse(text: String, render: TRERender): List<TREStyleTextTreeInter> {
        if (text.isEmpty()) return listOf(TRECoreContentLeaf(""))

        val spanParserMap = treInlineParserManager!!.getSpanParserMap()
        val preprocessReg = preprocessReg(text, treInlineParserManager!!.getRegParserMap())
        var pointer = 0
        var normalString = ""
        val resultList = mutableListOf<TREStyleTextTreeInter>()

        while(true) {
            if (pointer>=text.length) break
            val startChar = text[pointer]
            val spanParsers = spanParserMap[startChar]
            val regParsers = preprocessReg.get(pointer)
            if (regParsers.isEmpty()&&(spanParsers==null||spanParsers.isEmpty())){
                normalString += text[pointer]
                pointer++
                continue
            }
            val spanList:MutableList<InlineParser> = mutableListOf<InlineParser>().apply {
                addAll(regParsers)
            }
            spanParsers!!.forEach {
                if (it.formatCheck(text.substring(pointer))){
                    spanList.add(it)
                }
            }
            var weight = 0
            var parser: InlineParser? = null
            spanList.forEach {
                val w = it.getWeight(text.substring(pointer))
                if (w>weight){
                    weight = w
                    parser = it
                }
            }
            if (parser==null){
                normalString += text[pointer]
                pointer++
                continue
            }
            // 处理普通文本
            if (normalString.isNotEmpty()) {
                resultList.add(TRECoreContentLeaf(normalString))
                normalString = ""
            }
            val leaf = parser!!.generateLeaf(text.substring(pointer), render)
            resultList.add(leaf)
            pointer += leaf.originalSize()
        }
        if (normalString.isNotEmpty()) {
            resultList.add(TRECoreContentLeaf(normalString))
        }
        return resultList
    }

    private fun preprocessReg(text: String, regParserMap: HashMap<String, InlineParser>): RegPointTable {
        return RegPointTable().apply {
            for ((reg, parser) in regParserMap) {
                add(text, reg, parser)
            }
        }
    }


}