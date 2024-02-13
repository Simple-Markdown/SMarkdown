package indi.midreamsheep.app.tre.model.render.parser.paragraph.head

import androidx.compose.material3.Divider
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.LineParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser

@LineParser
@MapKey("#")
class HeadParser: ParagraphParser {

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun getAnnotatedString(
        text: TextFieldValue,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRETextRender {
        val level = getLevel(text.text)
        var subSequence = text.text.subSequence(level, text.text.length)
        if (subSequence.isNotEmpty()) subSequence = subSequence.subSequence(1, subSequence.length)
        val isDisplay = selection <= level+1&&line.isFocus.value

        val render = TRETextRender(line)

        render.styleTextTree = StyleTextHeadRoot(subSequence.toString(), level, isDisplay)
        render.suffixLineDecorations.add(
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