package indi.midreamsheep.app.tre.model.render.parser.paragraph.list.ordered

import indi.midreamsheep.app.tre.api.annotation.render.line.LineRegParser
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey

@LineRegParser
@MapKey("[0-9]+\\.")
class OrderedListParser: ParagraphParser {

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRETextRender {
        println("hello")
        return TRETextRender(line)
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}