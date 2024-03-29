package indi.midreamsheep.app.tre.model.parser.paragraph.list.unordered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserList
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.parser.paragraph.divide.DivideListener
import indi.midreamsheep.app.tre.model.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserList
@MapKey("-")
class UnorderedListParser: indi.midreamsheep.app.tre.model.parser.LineParser {

    @Injector
    val parser: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        return text.startsWith("- ")
    }

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreBlock
    ): TRERender {
        val render = TRERender(line)

        val isDisplay = selection<2

        render.styleText.styleTextTree = StyleTextUnorderedListRoot(
            isDisplay,
        )

        val parse = parser!!.parse(
            text = text.substring(2),
            selection = selection - 2,
            isFocus = line.isFocus.value,
            render = render
        )

        for (treStyleTextTree in parse) {
            render.styleText.styleTextTree!!.addChildren(treStyleTextTree)
        }
        render.styleText.prefixTextDecorations.add(
            Display {
                Box(
                    Modifier
                        .size(25.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(6.dp)
                            .clip(RoundedCornerShape(100))
                            .background(Color.Black)
                    ) {
                    }
                }
            }
        )
        render.listener = DivideListener()

        return render
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}