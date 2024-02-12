package indi.midreamsheep.app.tre.model.render.parser.span.italic

import indi.midreamsheep.app.tre.api.annotation.render.InLineParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.model.render.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.SpanParser
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@InLineParser
@MapKey("*")
class ItalicParser: SpanParser {

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
        selection: Int,
        isFocus: Boolean,
        render: TRETextRender
    ): TREStyleTextTree {
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
        val substring:String = text.substring(1, pointer - 1)

        val isDisplay = selection in 0..pointer&&isFocus

        val (childrenList) = spanParse!!.parse(
            substring,selection-1,isFocus,
            render
        )
        val italicLeaf = StyleTextItalicLeaf(isDisplay)
        italicLeaf.addChildren(childrenList)
        return italicLeaf
    }
}