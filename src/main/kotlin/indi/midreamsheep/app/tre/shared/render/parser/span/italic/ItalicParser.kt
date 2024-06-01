package indi.midreamsheep.app.tre.shared.render.parser.span.italic

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTreeInter
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@InLineParserList
@MapKey("*")
//TODO 重写计算算法
class ItalicParser: InlineParser {

    @Injector
    private val spanParse: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<2) return false
        //找到下一个不为*的位置
        var pointer = 0
        while (pointer<text.length) {
            if (text[pointer]!='*') break
            pointer++
        }
        if (pointer==text.length) return false
        //找到下一个为*的位置
        while (pointer<text.length) {
            if (text[pointer]=='*') return true
            pointer++
        }
        return false
    }

    override fun getWeight(text: String): Int {
        return 2
    }

    override fun generateLeaf(
        text: String,
        render: TRERender
    ): TREStyleTextTreeInter {
        var pointer = 1
        while (pointer<text.length-1) {
            if (text[pointer]=='*') break
            pointer++
        }
        //找到下一个不为*的位置
        pointer++
        while (pointer<text.length) {
            if (text[pointer]!='*') break
            pointer++
        }
        val substring = text.substring(1, pointer - 1)

        println("text: $text;subString: $substring")
        val (childrenList) = spanParse!!.parse(
            substring,
            render
        )
        val italicLeaf = StyleTextItalicLeaf().apply {
            addChildren(ItalicAffix())
            addChildren(childrenList)
            addChildren(ItalicAffix())
        }
        return italicLeaf
    }
}