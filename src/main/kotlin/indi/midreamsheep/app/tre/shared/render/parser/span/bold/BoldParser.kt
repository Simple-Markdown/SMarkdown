package indi.midreamsheep.app.tre.shared.render.parser.span.bold

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTreeInter
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import lombok.extern.slf4j.Slf4j

@InLineParserList
@MapKey("*")
@Slf4j
class BoldParser: InlineParser {

    @Injector
    private val spanParse: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<4) return false
        if (text[0]!='*'||text[1]!='*') return false
        var pointer = 2
        while (pointer<text.length-1) {
            if (text[pointer]=='*'&&text[pointer+1]=='*') return true
            pointer++
        }
        return false
    }

    override fun getWeight(text: String): Int = 4


    override fun generateLeaf(
        text: String,
        render: TRERender
    ): TREStyleTextTreeInter {
        var pointer = 2
        while (pointer<text.length-1) {
            if (text[pointer]=='*'&&text[pointer+1]=='*') break
            pointer++
        }
        //找到下一个不为*的位置
        pointer++
        while (pointer<text.length) {
            if (text[pointer]!='*') break
            pointer++
        }

        val value:String = text.substring(2, pointer - 2)

        val boldLeaf = StyleTextBoldLeaf().apply {
            addChildren(StyleTextBoldFix())
        }

        val list = spanParse!!.parse(value,render)

        list.forEach {
            boldLeaf.addChildren(it)
        }

        boldLeaf.addChildren(StyleTextBoldFix())
        return boldLeaf
    }
}