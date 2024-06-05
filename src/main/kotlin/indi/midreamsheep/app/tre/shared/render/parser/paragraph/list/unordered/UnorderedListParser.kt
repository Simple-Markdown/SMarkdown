package indi.midreamsheep.app.tre.shared.render.parser.paragraph.list.unordered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.parser.LineParser
import indi.midreamsheep.app.tre.shared.render.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.tool.id.IdUtil
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserMap
@MapKey("reg:- .*")
class UnorderedListParser: LineParser {

    @Injector
    val parser: TREInlineParser? = null

    companion object{
        val ID = IdUtil.generateId()
    }

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender(block)

        val isDisplay = true

        render.styleText.styleTextTree = StyleTextUnorderedListRoot(
            isDisplay,
        )

        val parse = parser!!.parse(
            text = text.substring(2),
            render = render
        )

        for (treStyleTextTree in parse) {
            render.styleText.styleTextTree!!.addChild(treStyleTextTree)
        }
        render.styleText.prefixTextDecorations.add(
            Display {
                {
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
            }
        )
        render.listener = UnorderedListListener(
            line = block,
            id = ID,
            styleTextTree = render.styleText.styleTextTree as StyleTextUnorderedListRoot
        )
        return render
    }
    override fun getWeight(text: String): Int {
        return 1
    }
}