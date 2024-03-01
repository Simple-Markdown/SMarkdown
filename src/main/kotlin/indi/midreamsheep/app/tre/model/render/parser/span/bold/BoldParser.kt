package indi.midreamsheep.app.tre.model.render.parser.span.bold

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.model.render.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.SpanParser
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import lombok.extern.slf4j.Slf4j

@InLineParser
@MapKey("*")
@Slf4j
class BoldParser: SpanParser {

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
        selection: Int,
        isFocus: Boolean,
        render: TRETextRender
    ): TREStyleTextTree {

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
        val isDisplay = selection in 0..pointer&&isFocus

        val substring:String = text.substring(2, pointer - 2)

        val boldLeaf = StyleTextBoldLeaf(
            substring,
            isDisplay
        )

        val list = spanParse!!.parse(substring,selection-2,isFocus, render)

        list.forEach {
            boldLeaf.addChildren(it)
        }
        return boldLeaf
    }
}