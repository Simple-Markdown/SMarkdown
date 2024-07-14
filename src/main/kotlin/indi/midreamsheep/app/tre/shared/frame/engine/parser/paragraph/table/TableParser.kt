package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

@LineParserMap
@MapKey("start:|")
class TableParser: TRELineStyleParser {

    override fun buildRender(text: String, block: TRECoreBlock): TRERender {
        TODO()
    }

    override fun getWeight(text: String): Int {
        TODO()
    }

    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        TODO()
    }
}