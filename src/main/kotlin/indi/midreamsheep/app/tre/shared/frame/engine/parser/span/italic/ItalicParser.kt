package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.italic

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treInlineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.tool.text.findAffixPoint

@InLineParserList
@MapKey("*")
class ItalicParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.TREInlineStyleParser {

    companion object{
        const val ITALIC_AFFIX = "*"
    }

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
            addChildren(treInlineParse(substring, render).toTypedArray())
            addChild(ItalicAffix())
        }
        return italicLeaf
    }
}