package indi.midreamsheep.app.markdown.editor.parser.impl.paragraph

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.editor.manager.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.ParagraphParser
import indi.midreamsheep.app.markdown.editor.parser.SpanParse
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class DefaultParser:ParagraphParser {

    @Injector
    private val spanParser:SpanParse? = null

    override fun formatCheck(text: String): Boolean {
        return true
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: MarkdownStateManager,
        state: MarkdownLineState
    ): @Composable () -> Unit {
        return {
            val (annotatedString, function0List) = spanParser!!.parse(text)
            Text(
                text= annotatedString,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        recall()
                    }
                    .padding(vertical =  5.dp)

            )
            for (function in function0List) {
                function()
            }
        }
    }
}