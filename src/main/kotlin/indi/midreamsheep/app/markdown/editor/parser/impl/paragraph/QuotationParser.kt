package indi.midreamsheep.app.markdown.editor.parser.impl.paragraph

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.editor.manager.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.editor.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
@MapInjector(target = "paragraph",key=">")
class QuotationParser: ParagraphParser {

    @Injector
    private val markdownParse:MarkdownParse? = null

    override fun formatCheck(text: String): Boolean {
        return text.trim()[0]=='>'
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: MarkdownStateManager,
        state: MarkdownLineState
    ):@Composable () -> Unit {
        return {
            Row(
                Modifier.fillMaxWidth().clickable {
                    recall()
                }.height(IntrinsicSize.Max).background(Color(0xFFDCDCDC))
            ) {
                Spacer(modifier = Modifier.width(5.dp).fillMaxHeight().background(Color.Green))
                Spacer(modifier = Modifier.width(5.dp))
                markdownParse!!.parse(text.substring(text.indexOf('>')+1),state,stateList,recall)()
            }
        }
    }

}