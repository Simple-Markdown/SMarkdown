package indi.midreamsheep.app.tre.shared.render.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.tool.id.IdUtil
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserMap
@MapKey(">")
class QuoteParser: indi.midreamsheep.app.tre.shared.render.parser.LineParser {

    @Injector
    val parser: indi.midreamsheep.app.tre.shared.render.parser.paragraph.TRELineParser? = null

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
        val render = TRERender(block)
        val level = getLevel(text)

        var isDisplay = selection<level*2
        if (selection>=level*2||blockManager.getCurrentBlock()!=block.lineState){
            block.propertySet.add(id)
        }
        val isContain = block.propertySet.contains(id)
        if(isContain) isDisplay = false
        render.styleText.styleTextTree = StyleTextQuoteRoot(
            level,
            isDisplay,
        )
        val parse = parser!!.parse(
            text.substring(level*2),
            selection-level*2,
            block,
            blockManager
        )

        if (isContain){
            render.offsetMap = object : TRERenderOffsetMap() {
                override fun getStartOffset() = level*2 + parse.offsetMap.getStartOffset()
            }
        }

        repeat(level) {
            render.styleText.prefixTextDecorations.add(
                Display {
                    {
                        Box(
                            Modifier.fillMaxHeight().width(5.dp).background(Color.Green)
                        ) {}
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            )
        }
        //设置背景颜色
        render.styleText.backgroundDecorations.add(
            Display {
                {
                    Box(
                        Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.1f))
                    ) {}
                }
            }
        )
        render.styleText.append(parse.styleText)
        render.listener = QuoteListener(
            block,
            id,
            parse.listener,
            render.styleText.styleTextTree as StyleTextQuoteRoot
        )
        if (selection>=level*2||blockManager.getCurrentBlock()!=block.lineState){
            block.propertySet.add(id)
        }
        return render
    }

    override fun getWeight(text: String): Int {
        return getLevel(text)*2
    }

    private fun getLevel(text: String): Int {
        var level = 0
        var point = 0
        while (point<text.length-1){
            if (text[point] == '>'&&text[point+1] == ' '){
                point += 2
                level++
                continue
            }
            break
        }
        return level
    }
}