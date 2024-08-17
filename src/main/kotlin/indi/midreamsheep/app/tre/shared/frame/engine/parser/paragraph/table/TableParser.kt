package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

@LineParserMap
@MapKey("reg:^\\|.+\\|\$")
class TableParser: TRELineStyleParser {
    override fun buildRender(text: String, block: TRECoreBlock): TRERender {
        val split = text.split('|')
        // 删除左边和右边的数据
        val contents = split.subList(1, split.size - 1)
        val render = TRERender(block);
        return render;
    }

    override fun getWeight(text: String) = 2
}