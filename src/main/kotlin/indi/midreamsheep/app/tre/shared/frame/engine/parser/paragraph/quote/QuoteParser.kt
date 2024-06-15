package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRECoreLineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserMap
@MapKey(">")
class QuoteParser: TRELineStyleParser {

    @Injector
    val parser: TRECoreLineParser? = null


    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        return getLevel(text) != -1
    }

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender(block)
        val level = getLevel(text)

        render.styleText.styleTextTree = StyleTextQuoteRoot(level).apply {
            addChild(StyleTextQuotePrefix(level,render))
        }
        val parse = parser!!.parse(
            text.substring(level*2),
            block,
        )

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