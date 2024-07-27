package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.link

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TREInlineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@InLineParserList
@MapKey("reg:(?<!!)\\[(.*?)\\]\\((.*?)\\)")
class LinkParser: TREInlineStyleParser {

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
        return StyleTextLinkLeaf(
            linkName = linkName,
            displayName = displayName,
            isDisplay =  true
        )
    }
}