package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.bold

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TREInlineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treInlineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.tool.text.findAffixPoint
import lombok.extern.slf4j.Slf4j

@InLineParserList
@MapKey("*")
@Slf4j
class BoldParser: TREInlineStyleParser {

    companion object{
        const val BOLD_AFFIX = "**"
    }

    override fun formatCheck(text: String): Boolean {
        findAffixPoint(text, BOLD_AFFIX).let { if (it.first==0&&it.second!=-1) return true }
        return false
    }

    override fun generateLeaf(
        text: String
    ): TREStyleTextTreeInter {
        val value:String = text.substring(2, findAffixPoint(text, BOLD_AFFIX).second)
        val boldLeaf = StyleTextBoldLeaf().apply {
            addChild(StyleTextBoldAffix())
            addChildren(treInlineParse(value).toTypedArray())
            addChild(StyleTextBoldAffix())
        }
        return boldLeaf
    }

    override fun getWeight(text: String): Int = 4
}