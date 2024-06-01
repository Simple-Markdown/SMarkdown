package indi.midreamsheep.app.tre.shared.render.parser.span

import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreContentLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TREInlineParser {

    @Injector
    private var treInlineParserManager: TREInlineParserManager? = null


    /**
     * 块解析器
     * 参数：text 将要解析的文本
     *          offsetMapping 偏移量映射
     * */
    fun parse(
        text: String,
        render: TRERender
    ): List<TREStyleTextTreeInter>
    {

        if (text.isEmpty()){
            return listOf(
                TRECoreContentLeaf("")
            )
        }

        var transformedPoint = 0
        var originalOffsetStart = 0

        val resultList = mutableListOf<TREStyleTextTreeInter>()

        var normalString = ""

        while(true) {
            if (originalOffsetStart>=text.length) break
            //获取处理器
            val startChar = text[originalOffsetStart]
            val spanParsers = treInlineParserManager!!.get(startChar)
            if (spanParsers.isEmpty()) {
                normalString += text[originalOffsetStart]
                originalOffsetStart++
                continue
            }
            val spanList:MutableList<InlineParser> = mutableListOf()
            spanParsers.forEach {
                if (it.formatCheck(text.substring(originalOffsetStart))){
                    spanList.add(it)
                }
            }
            var weight = 0
            var parser: indi.midreamsheep.app.tre.shared.render.parser.InlineParser? = null
            spanList.forEach {
                val w = it.getWeight(text.substring(originalOffsetStart))
                if (w>weight){
                    weight = w
                    parser = it
                }
            }
            if (parser==null){
                normalString += text[originalOffsetStart]
                originalOffsetStart++
                continue
            }

            // 处理普通文本
            if (normalString.isNotEmpty()) {
                resultList.add(TRECoreContentLeaf(normalString))
                transformedPoint += normalString.length
                normalString = ""
            }

            //进行解析
            val leaf = parser!!.generateLeaf(
                text.substring(originalOffsetStart),
                render
            )

            transformedPoint += leaf.transformedSize()
            originalOffsetStart += leaf.originalSize()
            resultList.add(leaf)
        }
        if (normalString.isNotEmpty()) {
            resultList.add(TRECoreContentLeaf(normalString))
        }
        return resultList
    }

}