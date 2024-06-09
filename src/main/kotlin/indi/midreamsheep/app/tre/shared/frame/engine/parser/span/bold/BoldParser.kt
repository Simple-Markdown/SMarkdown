package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.bold

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.tool.text.findAffixPoint
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import lombok.extern.slf4j.Slf4j

@InLineParserList
@MapKey("*")
@Slf4j
class BoldParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.InlineParser {

    companion object{
        const val BOLD_AFFIX = "**"
    }

    @Injector
    private val spanParse: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        findAffixPoint(text, BOLD_AFFIX).let {
            if (it.first!=-1&&it.second!=-1) return true
        }
        return false
    }

    override fun generateLeaf(
        text: String,
        render: TRERender
    ): TREStyleTextTreeInter {
        val value:String = text.substring(2, findAffixPoint(text, BOLD_AFFIX).second)
        val boldLeaf = StyleTextBoldLeaf().apply {
            addChild(StyleTextBoldAffix())
            addChildren(spanParse!!.parse(value,render).toTypedArray())
            addChild(StyleTextBoldAffix())
        }
        return boldLeaf
    }

    override fun getWeight(text: String): Int = 4
}