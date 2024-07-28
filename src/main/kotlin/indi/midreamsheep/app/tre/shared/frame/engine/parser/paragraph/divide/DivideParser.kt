package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.divide

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

@LineParserMap
@MapKey("reg:^-{3}\$|^\\*{3}\$|_{3}")
class DivideParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser {

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val apply = TRECoreContentLeaf(text)
        val tree = TRECoreTreeRoot()
        tree.addChild(apply)

        val render = TRERender(block)
        render.styleText.styleTextTree = tree
        render.styleText.previewDisplay = Display {
            {
                Column(
                    modifier = Modifier
                        .height(20.dp)
                        .clickable {
                            // 获取当前点击的位置

                        },
                    verticalArrangement = Arrangement.Center
                ) {
                    Divider()
                }
            }
        }
        return render
    }

    override fun getWeight(text: String) = 3
}