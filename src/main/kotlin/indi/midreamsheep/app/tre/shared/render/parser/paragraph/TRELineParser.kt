package indi.midreamsheep.app.tre.shared.render.parser.paragraph

import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRELineParser {

    @Injector
    private val defaultParser: indi.midreamsheep.app.tre.shared.render.parser.paragraph.TRELineParserManager? = null

    fun parse(
        text: String,
        selection: Int,
        state: TRECoreBlock,
        stateList: TREBlockManager
    ): TRERender {
        if(text.isEmpty()) {
            val treCoreStyleTextRoot = TRECoreStyleTextRoot()
            treCoreStyleTextRoot.addChildren(TRECoreLeaf(""))
            val render = TRERender(state)
            render.styleText.styleTextTree = treCoreStyleTextRoot
            state.propertySet.clear()
            return render
        }
        return defaultParser!!.get(text).getAnnotatedString(text,selection,stateList,state)
    }
}