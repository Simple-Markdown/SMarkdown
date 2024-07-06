package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.ordered

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

@LineParserMap
@MapKey("reg:[0-9]+\\. .*")
class OrderedListParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser {

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        //通过正则表达式获取
        val int = text.substring(0, text.indexOf(".")).toInt()
        println(int)
        return TRERender(block)
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}