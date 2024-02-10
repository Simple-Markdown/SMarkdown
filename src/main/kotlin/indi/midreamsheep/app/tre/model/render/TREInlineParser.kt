package indi.midreamsheep.app.tre.model.render

import indi.midreamsheep.app.tre.context.api.annotation.render.InLineParser
import indi.midreamsheep.app.tre.model.render.parser.SpanParser
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.model.render.styletext.leaf.TRECoreLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREInlineParser {

    @InLineParser
    private val spanParserList = HashMap<Char, List<SpanParser>>()


    /**
     * 块解析器
     * 参数：text 将要解析的文本
     *          selection 光标位置，光标位置为相对位置，即相对与text的位置
     *          isFocus 是否拥有焦点
     *          offsetMapping 偏移量映射
     * */
    fun parse(
        text: String,
        selection: Int,
        isFocus: Boolean,
        offsetMapping: TREStyleTextOffsetMapping,
        render: TRETextRender
    ): List<TREStyleTextTree>
    {

        if (text.isEmpty()){
            return listOf(
                TRECoreLeaf("",offsetMapping)
            )
        }

        var transformedPoint = 0
        var originalOffsetStart = 0

        val resultList = mutableListOf<TREStyleTextTree>()

        var normalString = ""

        while(true) {
            if (originalOffsetStart>=text.length) break
            //获取处理器
            val startChar = text[originalOffsetStart]
            val spanParsers = spanParserList[startChar]
            if (spanParsers==null) {
                normalString += text[originalOffsetStart]
                originalOffsetStart++
                continue
            }
            val spanList:MutableList<SpanParser> = mutableListOf()
            spanParsers.forEach {
                if (it.formatCheck(text.substring(originalOffsetStart))){
                    spanList.add(it)
                }
            }
            var weight = 0
            var parser: SpanParser? = null
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
                resultList.add(TRECoreLeaf(normalString,
                    TREStyleTextOffsetMapping(originalOffsetStart+offsetMapping.originalOffsetStart - normalString.length, transformedPoint+offsetMapping.transformedOffsetStart)
                )
                )
                transformedPoint += normalString.length
                normalString = ""
            }

            //进行解析
            val leaf = parser!!.generateLeaf(
                text.substring(originalOffsetStart),
                selection - originalOffsetStart,
                isFocus,
                TREStyleTextOffsetMapping(
                    originalOffsetStart+offsetMapping.originalOffsetStart,
                    transformedPoint+offsetMapping.transformedOffsetStart
                ),
                render
            )

            transformedPoint += leaf.transformedSize()
            originalOffsetStart += leaf.originalSize()
            resultList.add(leaf)
        }
        if (normalString.isNotEmpty()) {
            resultList.add(TRECoreLeaf(normalString,
                TREStyleTextOffsetMapping(originalOffsetStart+offsetMapping.originalOffsetStart-normalString.length, transformedPoint+offsetMapping.transformedOffsetStart)
            )
            )
        }
        return resultList
    }

}