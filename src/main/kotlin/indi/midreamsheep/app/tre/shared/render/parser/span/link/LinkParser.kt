package indi.midreamsheep.app.tre.shared.render.parser.span.link

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.render.parser.InlineParser
import indi.midreamsheep.app.tre.shared.render.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTreeInter
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@InLineParserList
@MapKey("[")
class LinkParser: InlineParser {

    @Injector
    private val spanParse: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<4) return false
        //找到下一个]
        var pointer = 1
        while (pointer<text.length) {
            if (text[pointer]==']') break
            pointer++
        }
        if (pointer+2>=text.length||text[pointer+1]!='(') return false
        //找到下一个)
        pointer++
        while (pointer<text.length) {
            if (text[pointer]==')') return true
            pointer++
        }
        return false
    }

    override fun getWeight(text: String): Int {
        return 4
    }

    override fun generateLeaf(
        text: String,
        render: TRERender
    ): TREStyleTextTreeInter {
        var pointer = 1
        while (pointer <= text.length - 1) {
            if (text[pointer] == ']') break
            pointer++
        }
        val displayName: String = text.substring(1, pointer)
        pointer++
        //找到()中的内容
        var link = ""
        val linkStart = pointer
        while (pointer <= text.length - 1) {
            if (text[pointer] == ')') break
            link += text[pointer]
            pointer++
        }

        val linkName = text.substring(linkStart+1, pointer)

        //TODO rebuild link parser
        return StyleTextLinkLeaf(
            linkName = linkName,
            displayName = displayName,
            isDisplay =  true
        )
    }
}