package indi.midreamsheep.app.tre.model.render.parser.paragraph.list.unordered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.LineParser
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParser
@MapKey("-")
class UnorderedListParser: ParagraphParser {

    @Injector
    val parser:TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        return text.startsWith("- ")
    }

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRETextRender {
        val render = TRETextRender(line)

        val isDisplay = line.isFocus.value&&selection<=2

        render.styleTextTree = StyleTextUnorderedListRoot(
            isDisplay,
        )

        val parse = parser!!.parse(
            text = text.substring(2),
            selection = selection - 2,
            isFocus = line.isFocus.value,
            render = render
        )

        for (treStyleTextTree in parse) {
            render.styleTextTree!!.addChildren(treStyleTextTree)
        }
        render.prefixTextDecorations.add(
            Display {
                Box(
                    Modifier
                        .size(25.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                    ) {
                    }
                }
            }
        )
        return render
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}