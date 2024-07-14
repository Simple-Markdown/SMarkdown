package indi.midreamsheep.app.tre.shared.frame.engine.parser.span

import indi.midreamsheep.app.tre.shared.frame.engine.parser.TREInlineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TREInlineParser {

    @Injector
    private var treInlineParserManager: TREInlineParserManager? = null


    fun parse(text: String, render: TRERender): List<TREStyleTextTreeInter> {
        // 如果文本为空的话返回默认节点
        if (text.isEmpty()) return listOf(TRECoreContentLeaf(""))
        // 获取起始符解析器
        val spanParserMap = treInlineParserManager!!.getSpanParserMap()
        // 引用
        var pointer = 0
        // 当前默认样式的文本数据
        var normalString = ""
        // 结果样式树结构
        val resultList = mutableListOf<TREStyleTextTreeInter>()

        while(true) {
            // 如果当前的指针超过了文本长度就停止循环
            if (pointer>=text.length) break
            // 通过起始符获取目标的解析器
            val parserList = mutableListOf<TREInlineStyleParser>()
                .apply {
                    spanParserMap[text[pointer]]?.let { treInlineStyleParsers ->
                        treInlineStyleParsers.forEach {
                            if (it.formatCheck(text.substring(pointer))){
                                add(it)
                            }
                        }
                    }
                }
            // 进行正则表达式获取解析器
            treInlineParserManager!!.getRegParserMap().forEach{ (reg, parser) ->
                val find = reg.toRegex().find(text, pointer)
                if(find != null) {
                    val first = find.range.first
                    if(first == pointer){
                        parserList.add(parser)
                    }
                }
            }

            if (parserList.isEmpty()){
                normalString += text[pointer]
                pointer++
                continue
            }
            var weight = 0
            var parser: TREInlineStyleParser? = null
            parserList.forEach {
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
}