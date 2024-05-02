package indi.midreamsheep.app.tre.shared.render.parser.span

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.api.inter.manager.TREMapManager
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREInlineParserManager:TREMapManager<Char,List<indi.midreamsheep.app.tre.shared.render.parser.InlineParser>> {

    @InLineParserList
    private val spanParserList = HashMap<Char, List<indi.midreamsheep.app.tre.shared.render.parser.InlineParser>>()

    override fun get(key: Char): List<indi.midreamsheep.app.tre.shared.render.parser.InlineParser> {
        if (!spanParserList.containsKey(key)) return listOf()
        return spanParserList[key]!!
    }

    override fun get(command: String) = get(command[0])


}