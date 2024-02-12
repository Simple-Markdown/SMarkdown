package indi.midreamsheep.app.tre.model.render.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.LineParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRELineParser
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParser
@MapKey(">")
class QuoteParser: ParagraphParser {

    @Injector
    val parser:TRELineParser? = null

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun getAnnotatedString(
        text: TextFieldValue,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRETextRender {
        val render = TRETextRender(line)
        val level = getLevel(text.text)

        val isDisplay = line.isFocus.value&&text.selection.start<=level*2
        render.styleTextTree = StyleTextQuoteRoot(
            level,
            isDisplay,
        )
        val parse = parser!!.parse(
            text.copy(
                text = text.text.substring(level*2),
            ),
            selection-level*2,
            line,
            stateList
        )
        if (!isDisplay) {
            render.prefixTextDecorations.add(
                Display {
                    repeat(level) {
                        Box(
                            Modifier.fillMaxHeight().width(5.dp).background(Color.Green)
                        ) {}
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            )
            //设置背景颜色
            render.backgroundDecorations.add(
                Display {
                    Box(
                        Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.2f))
                    ) {}
                }
            )
        }
        render.prefixLineDecorations.addAll(parse.prefixLineDecorations)
        render.suffixLineDecorations.addAll(parse.suffixLineDecorations)
        render.suffixTextDecorations.addAll(parse.suffixTextDecorations)
        render.previewAnnotation.putAll(parse.previewAnnotation)
        render.prefixTextDecorations.addAll(parse.prefixTextDecorations)
        render.backgroundDecorations.addAll(parse.backgroundDecorations)
        render.previewDisplay = parse.previewDisplay
        render.styleTextTree!!.addChildren(parse.styleTextTree!!)
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