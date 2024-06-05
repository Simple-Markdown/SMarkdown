package indi.midreamsheep.app.tre.shared.render.parser.paragraph.list.ordered

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.render.TRERender

@LineParserMap
@MapKey("reg:[0-9]+\\. .*")
class OrderedListParser: indi.midreamsheep.app.tre.shared.render.parser.LineParser {

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        println("hello")
        return TRERender(block)
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}