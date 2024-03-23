package indi.midreamsheep.app.tre.model.parser.paragraph.list.ordered

import indi.midreamsheep.app.tre.api.annotation.render.line.LineRegParser
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey

@LineRegParser
@MapKey("[0-9]+\\.")
class OrderedListParser: indi.midreamsheep.app.tre.model.parser.LineParser {

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreBlock
    ): TRERender {
        println("hello")
        return TRERender(line)
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}