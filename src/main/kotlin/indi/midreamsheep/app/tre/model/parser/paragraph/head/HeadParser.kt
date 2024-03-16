package indi.midreamsheep.app.tre.model.parser.paragraph.head

import androidx.compose.material3.Divider
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserList
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.model.render.style.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey

@LineParserList
@MapKey("#")
class HeadParser: indi.midreamsheep.app.tre.model.parser.LineParser {

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRERender {
        val level = getLevel(text)
        var subSequence = text.subSequence(level, text.length)
        if (subSequence.isNotEmpty()) subSequence = subSequence.subSequence(1, subSequence.length)
        val isDisplay = selection <= level

        val render = TRERender(line)

        render.styleText.styleTextTree = StyleTextHeadRoot(level, isDisplay).apply {
            addChildren(
                TRECoreLeaf(subSequence.toString())
            )
        }
        render.styleText.suffixLineDecorations.add(
            Display {
                Divider()
            }
        )
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