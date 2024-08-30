package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.highlight

import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treInlineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.tool.text.findAffixPoint
import lombok.extern.slf4j.Slf4j

@InLineParserList
@MapKey("(")
@Slf4j
class HighlightParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.TREInlineStyleParser {

    override fun formatCheck(text: String): Boolean {
        findAffixPoint(text,"(",")",0).let {
            if (it.first == 0 && it.second != -1) return true
        }
        return false
    }

    override fun getWeight(text: String): Int = 2

    override fun generateLeaf(
        text: String
    ): TREStyleTextTree {
        val root = TRECoreTreeRoot().apply {
            addChild(TRECoreContentLeaf("("))
            addChild(
                StyleTextHighlightLeaf().apply {
                    for (treStyleTextTreeInter in treInlineParse(text.substring(1, findAffixPoint(text,"(",")",0).second))) {
                        addChild(treStyleTextTreeInter)
                    }
                }
            )
            addChild(TRECoreContentLeaf(")"))
        }
        return root
    }
}