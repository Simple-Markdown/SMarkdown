package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.head

import androidx.compose.material3.Divider
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

@LineParserMap
@MapKey("start:#")
class HeadParser: TRELineStyleParser {

    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        return getLevel(text) != -1
    }

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val level = getLevel(text)
        var subSequence = text.subSequence(level, text.length)
        if (subSequence.isNotEmpty()) subSequence = subSequence.subSequence(1, subSequence.length)
        val render = TRERender()
        render.styleText.styleTextTree = StyleTextHeadRoot(level).apply {
            addChild(
                StyleTextHeadPrefix(level,subSequence.isNotEmpty())
            )
            addChild(
                TRECoreContentLeaf(subSequence.toString())
            )
        }
        render.styleText.suffixLineDecorations.add(
            Display {
                {
                    Divider()
                }
            }
        )

        render.trePreButton = TRELinePreButton{
            Display{
                {
                HeadButton(level,block, render.styleText.styleTextTree as StyleTextHeadRoot,block.getBlockManager())
                }

            }
        }
        return render
    }

    override fun getWeight(text: String): Int {
        return getLevel(text)+1
    }

    private fun getLevel(text: String): Int {
        var level = 0
        for (c in text) {
            if (c == '#') level++
            else if (c == ' ') break
            else return -1
        }
        if (level > 6) return -1
        return level
    }
}