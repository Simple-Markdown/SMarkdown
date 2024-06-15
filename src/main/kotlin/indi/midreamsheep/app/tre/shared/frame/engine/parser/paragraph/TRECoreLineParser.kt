package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph

import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class TRECoreLineParser:TRELineParser {

    @Injector
    private val defaultParser: TRELineParserManager? = null

    override fun parse(
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
                text,block.getBlockManager(),
                block.getBlockManager().indexOf(block)
            )
            .buildRender(text,block)
    }
}