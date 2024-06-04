package indi.midreamsheep.app.tre.shared.render.parser.paragraph.divide

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
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot

@LineParserMap
@MapKey("-")
class DivideParser: indi.midreamsheep.app.tre.shared.render.parser.LineParser {

    override fun formatCheck(text: String): Boolean {
        val content = text.trim()
        if (content.length<3) return false
        if (content[1]!='-'||content[2]!='-'||content[0]!='-') return false
        //如果之后是空格或者换行符，那么就是分割线
        if (content.length==3) return true
        val pointer = 3
        for (i in pointer until content.length) {
            if (content[i]!=' '&&content[i]!='\n'&&content[i]!='-'){
                return false
            }
        }
        return true
    }

    override fun buildRender(
        text: String,
        selection:Int,
        blockManager: TREBlockManager,
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
                            block.focus()
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