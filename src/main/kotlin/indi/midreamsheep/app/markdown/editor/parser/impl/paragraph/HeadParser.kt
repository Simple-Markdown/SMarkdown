package indi.midreamsheep.app.markdown.editor.parser.impl.paragraph

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.editor.line.core.CoreMarkdownLine
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.ParagraphParser
import indi.midreamsheep.app.markdown.editor.parser.SpanParse
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@MapInjector(target = "paragraph",key="#")
@Comment
class HeadParser:ParagraphParser {

    @Injector
    private val spanParser: SpanParse? = null;

    override fun formatCheck(text: String): Boolean {
        return getLevel(text) != -1
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: MarkdownStateManager,
        state: CoreMarkdownLine
    ): @Composable () -> Unit {
        val level = getLevel(text)
        var subSequence = text.subSequence(level, text.length)
        if (subSequence.isNotEmpty()) subSequence = subSequence.subSequence(1, subSequence.length)
        //行内解析
        return {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = (20 + (6 - level) * 5).sp
                        )
                    ) {
                        append(subSequence)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        recall()
                    }
                    .padding(vertical =  5.dp)
            )
        }
    }

    /**
     * text解析，用于对文本进行初始化解释时调用
     * @return Pair<lineNumber,innerNumber> 两者都是下一次解析的起始位置
     * */
    override fun analyse(texts: List<String>, lineNumber: Int, innerNumber: Int): Pair<Int, Int> {
        TODO("Not yet implemented")
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