package indi.midreamsheep.app.tre.shared.render.parser.span.highlight

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTree
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import lombok.extern.slf4j.Slf4j

@InLineParserList
@MapKey("(")
@Slf4j
class HighlightParser: InlineParser {

    @Injector
    private val spanParse: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<2) return false
        //找到下一个)
        var pointer = 1
        while (pointer<text.length) {
            if (text[pointer]==')') return true
            pointer++
        }
        return false
    }

    override fun getWeight(text: String): Int = 2

    override fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        render: TRERender
    ): TREStyleTextTree {

        var pointer = 1
        while (pointer<text.length-1) {
            if (text[pointer]==')') break
            pointer++
        }

        val display = selection in 1..pointer&&isFocus

        val substring:String = text.substring(1, pointer)

        val highlightLeaf = StyleTextHighlightLeaf(substring,display)

        val list = spanParse!!.parse(substring,selection-1,isFocus,
            render
        )

        list.forEach {
            highlightLeaf.addChildren(it)
        }
        return highlightLeaf
    }
}