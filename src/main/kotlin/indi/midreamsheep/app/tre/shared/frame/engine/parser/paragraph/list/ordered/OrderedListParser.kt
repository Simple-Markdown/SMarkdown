package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.ordered

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

@LineParserMap
@MapKey("reg:[0-9]+\\. .*")
class OrderedListParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser {

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