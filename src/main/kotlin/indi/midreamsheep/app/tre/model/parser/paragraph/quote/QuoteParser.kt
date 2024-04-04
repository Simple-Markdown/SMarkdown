package indi.midreamsheep.app.tre.model.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.parser.LineParser
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserMap
@MapKey(">")
class QuoteParser: LineParser {

    @Injector
    val parser: indi.midreamsheep.app.tre.model.parser.paragraph.TRELineParser? = null

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreBlock
    ): TRERender {
        val render = TRERender(line)
        val level = getLevel(text)

        val isDisplay = selection<level*2
        render.styleText.styleTextTree = StyleTextQuoteRoot(
            level,
            isDisplay,
        )
        val parse = parser!!.parse(
            text.substring(level*2),
            selection-level*2,
            line,
            stateList
        )
        repeat(level) {
            render.styleText.prefixTextDecorations.add(
                Display {
                    Box(
                        Modifier.fillMaxHeight().width(5.dp).background(Color.Green)
                    ) {}
                    Spacer(modifier = Modifier.width(5.dp))
                }
            )
        }
        //设置背景颜色
        render.styleText.backgroundDecorations.add(
            Display {
                Box(
                    Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.1f))
                ) {}
            }
        )
        render.styleText.prefixLineDecorations.addAll(parse.styleText.prefixLineDecorations)
        render.styleText.suffixLineDecorations.addAll(parse.styleText.suffixLineDecorations)
        render.styleText.suffixTextDecorations.addAll(parse.styleText.suffixTextDecorations)
        render.styleText.previewAnnotation.putAll(parse.styleText.previewAnnotation)
        render.styleText.prefixTextDecorations.addAll(parse.styleText.prefixTextDecorations)
        render.styleText.backgroundDecorations.addAll(parse.styleText.backgroundDecorations)
        render.styleText.previewDisplay = parse.styleText.previewDisplay
        render.styleText.styleTextTree!!.addChildren(parse.styleText.styleTextTree!!)
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