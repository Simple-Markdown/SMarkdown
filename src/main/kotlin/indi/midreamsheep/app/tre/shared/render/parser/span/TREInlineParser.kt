package indi.midreamsheep.app.tre.shared.render.parser.span

import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TREInlineParser {

    @Injector
    private var treInlineParserManager: TREInlineParserManager? = null


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
        render: TRERender
    ): List<TREStyleTextTree>
    {

        if (text.isEmpty()){
            return listOf(
                TRECoreLeaf("")
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
            val spanParsers = treInlineParserManager!!.get(startChar)
            if (spanParsers.isEmpty()) {
                normalString += text[originalOffsetStart]
                originalOffsetStart++
                continue
            }
            val spanList:MutableList<indi.midreamsheep.app.tre.shared.render.parser.InlineParser> = mutableListOf()
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
                resultList.add(TRECoreLeaf(normalString))
                transformedPoint += normalString.length
                normalString = ""
            }

            //进行解析
            val leaf = parser!!.generateLeaf(
                text.substring(originalOffsetStart),
                selection - originalOffsetStart,
                isFocus,
                render
            )

            transformedPoint += leaf.transformedSize()
            originalOffsetStart += leaf.originalSize()
            resultList.add(leaf)
        }
        if (normalString.isNotEmpty()) {
            resultList.add(TRECoreLeaf(normalString))
        }
        return resultList
    }

}