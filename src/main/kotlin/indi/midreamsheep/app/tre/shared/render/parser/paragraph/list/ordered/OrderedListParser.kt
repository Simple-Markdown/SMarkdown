package indi.midreamsheep.app.tre.shared.render.parser.paragraph.list.ordered

import indi.midreamsheep.app.tre.api.annotation.render.line.LineRegParser
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey

@LineRegParser
@MapKey("[0-9]+\\.")
class OrderedListParser: indi.midreamsheep.app.tre.shared.render.parser.LineParser {

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREBlockManager,
        line: TRECoreBlock
    ): TRERender {
        println("hello")
        return TRERender(line)
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}