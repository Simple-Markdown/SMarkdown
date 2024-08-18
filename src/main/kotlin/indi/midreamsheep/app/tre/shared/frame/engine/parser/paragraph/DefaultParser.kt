package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph

import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treInlineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class DefaultParser: TRELineStyleParser {

    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        return true
    }

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender(block)
        val treCoreStyleTextRoot = TRECoreTreeRoot()
        render.styleText.styleTextTree = treCoreStyleTextRoot

        val list = treInlineParse(text)
        for (styleTextLeaf in list) {
            treCoreStyleTextRoot.addChild(styleTextLeaf)
        }
        return render
    }

    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
    override fun getWeight(text: String): Int {
        return 0
    }

}