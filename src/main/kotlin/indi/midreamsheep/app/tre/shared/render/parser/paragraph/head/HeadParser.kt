package indi.midreamsheep.app.tre.shared.render.parser.paragraph.head

import androidx.compose.material3.Divider
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.parser.LineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.render.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.tool.id.IdUtil

@LineParserMap
@MapKey("#")
class HeadParser: LineParser {

    companion object{
        val id = IdUtil.generateId()
    }

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun buildRender(
        text: String,
        selection:Int,
        blockManager: TREBlockManager,
        block: TRECoreBlock
    ): TRERender {
        val level = getLevel(text)
        var subSequence = text.subSequence(level, text.length)
        if (subSequence.isNotEmpty()) subSequence = subSequence.subSequence(1, subSequence.length)
        val render = TRERender(block)
        var isDisplay = selection <= level+1

        if (selection>level+1||blockManager.getCurrentBlock()!=block.lineState){
            block.propertySet.add(id)
        }
        if (block.propertySet.contains(id)){
            render.offsetMap = object : TRERenderOffsetMap() {
                override fun getStartOffset() = level+1

            }
            isDisplay = false
        }
        render.styleText.styleTextTree = StyleTextHeadRoot(level, isDisplay).apply {
            addChildren(
                TRECoreLeaf(subSequence.toString())
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
            block,id,
            render.styleText.styleTextTree as StyleTextHeadRoot
        )

        render.trePreButton = TRELinePreButton{
            Display{
                {
                HeadButton(level,block, render.styleText.styleTextTree as StyleTextHeadRoot,blockManager)
                }
            }
        }
        return render
    }

    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
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