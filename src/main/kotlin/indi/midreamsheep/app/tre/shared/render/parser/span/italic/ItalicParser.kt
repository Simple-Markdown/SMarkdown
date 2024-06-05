package indi.midreamsheep.app.tre.shared.render.parser.span.italic

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.tool.text.findAffixPoint
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@InLineParserList
@MapKey("*")
class ItalicParser: InlineParser {

    companion object{
        const val ITALIC_AFFIX = "*"
    }

    @Injector
    private val spanParse: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        findAffixPoint(text, ITALIC_AFFIX).let {
            if (it.first!=-1&&it.second!=-1) return true
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
        val substring = text.substring(1, findAffixPoint(text, ITALIC_AFFIX).second)
        val italicLeaf = StyleTextItalicLeaf().apply {
            addChild(ItalicAffix())
            addChildren(spanParse!!.parse(substring, render).toTypedArray())
            addChild(ItalicAffix())
        }
        return italicLeaf
    }
}