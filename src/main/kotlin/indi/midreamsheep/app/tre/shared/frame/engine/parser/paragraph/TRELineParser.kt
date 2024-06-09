package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph

import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

interface TRELineParser {
    fun parse(text: String, block: TRECoreBlock, ): TRERender
}