package indi.midreamsheep.app.tre.model.editor.parser.impl.paragraph.head

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.tre.model.editor.line.core.CoreDefaultDisplay
import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.parser.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@MapInjector(target = "paragraph",key="#")
@Comment
class HeadParser: ParagraphParser {

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun getAnnotatedString(
        text: TextFieldValue,
        stateList: TREStateManager,
        state: CoreTRELine,
        recall: () -> Unit
    ): Pair<TREStyleTextTree, Display> {
        val level = getLevel(text.text)
        var subSequence = text.text.subSequence(level, text.text.length)
        if (subSequence.isNotEmpty()) subSequence = subSequence.subSequence(1, subSequence.length)
        val isDisplay = text.selection.start <= level+1&&state.isFocus.value
        return Pair(StyleTextHeadRoot(subSequence.toString(), level, isDisplay), CoreDefaultDisplay(state))
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