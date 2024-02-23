package indi.midreamsheep.app.tre.model.render.parser.span.link

import indi.midreamsheep.app.tre.api.annotation.render.InLineParser
import indi.midreamsheep.app.tre.model.render.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.SpanParser
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@InLineParser
@MapKey("[")
class LinkParser: SpanParser {

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
        selection: Int,
        isFocus: Boolean,
        render: TRETextRender
    ): TREStyleTextTree {
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

        val isDisplay = selection in 0..pointer+1 && isFocus
        println("linkName: $linkName, displayName: $displayName, isDisplay: $isDisplay")
        println(text)
        return StyleTextLinkLeaf(
            linkName = linkName,
            displayName = displayName,
            isDisplay =  isDisplay
        )
    }
}