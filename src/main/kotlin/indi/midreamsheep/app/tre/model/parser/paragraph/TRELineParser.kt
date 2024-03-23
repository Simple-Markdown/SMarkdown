package indi.midreamsheep.app.tre.model.parser.paragraph

import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.model.render.style.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.render.style.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRELineParser {

    @Injector
    private val defaultParser: indi.midreamsheep.app.tre.model.parser.paragraph.TRELineParserManager? = null

    fun parse(
        text: String,
        selection: Int,
        state: TRECoreBlock,
        stateList: TREStateManager
    ): TRERender {
        if(text.isEmpty()) {
            val treCoreStyleTextRoot = TRECoreStyleTextRoot()
            treCoreStyleTextRoot.addChildren(TRECoreLeaf(""))
            val render = TRERender(state)
            render.styleText.styleTextTree = treCoreStyleTextRoot
            return render
        }
        return defaultParser!!.get(text).getAnnotatedString(text,selection,stateList,state)
    }
}