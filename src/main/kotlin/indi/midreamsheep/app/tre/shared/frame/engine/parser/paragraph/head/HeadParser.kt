package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.head

import androidx.compose.material3.Divider
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf

@LineParserMap
@MapKey("start:#")
class HeadParser: LineParser {

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
        val render = TRERender(block)
        render.styleText.styleTextTree = StyleTextHeadRoot(level).apply {
            addChild(
                StyleTextHeadPrefix(level,render)
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

        render.listener = HeadListener(
            render.styleText.styleTextTree as StyleTextHeadRoot
        )

        render.trePreButton = TRELinePreButton{
            Display{
                {
                HeadButton(level,block, render.styleText.styleTextTree as StyleTextHeadRoot,block.getLineState().blockManager)
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

@LineParserMap
@MapKey("reg:^[-=]+")
class HeadCrossParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser {

    override fun buildRender(text: String, block: TRECoreBlock): TRERender {
        val treRender = TRERender(block)
        val blockManager = block.getLineState().blockManager
        val lastBlock = blockManager.getTREBlock(blockManager.indexOf(block.getLineState()) - 1)
        treRender.styleText.styleTextTree = StyleTextCrossHeadRoot(lastBlock.block as TRECoreBlock).apply {
            addChild(TRECoreContentLeaf(text))
        }
        return treRender
    }

    override fun getWeight(text: String) = 1

}