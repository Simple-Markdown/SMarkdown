package indi.midreamsheep.app.tre.shared.render.parser.paragraph

import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRECoreLineParser {

    @Injector
    private val defaultParser: TRELineParserManager? = null

    fun parse(
        text: String,
        block: TRECoreBlock,
    ): TRERender {
        if(text.isEmpty()) {
            val treCoreStyleTextRoot = TRECoreTreeRoot().apply {
                addChild(TRECoreContentLeaf(""))
            }
            val render = TRERender(block)
            render.styleText.styleTextTree = treCoreStyleTextRoot
            return render
        }
        return defaultParser!!
            .get(
                text,block.lineState.blockManager,
                block.lineState.blockManager.indexOf(block.lineState)
            )
            .buildRender(text,block)
    }
}