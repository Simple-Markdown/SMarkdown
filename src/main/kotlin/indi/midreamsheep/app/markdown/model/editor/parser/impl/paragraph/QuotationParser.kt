package indi.midreamsheep.app.markdown.model.editor.parser.impl.paragraph

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.model.editor.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
@MapInjector(target = "paragraph",key=">")
class QuotationParser: ParagraphParser {

    @Injector
    private val markdownParse: MarkdownParse? = null

    override fun formatCheck(text: String): Boolean {
        return text.trim()[0]=='>'
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager,
        state: indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
    ):@Composable () -> Unit {
        return {
            Row(
                Modifier.fillMaxWidth().clickable {
                    recall()
                }.height(IntrinsicSize.Max).background(Color(0xffeaeaea))
            ) {
                Spacer(modifier = Modifier.width(5.dp).fillMaxHeight().background(Color.Green))
                Spacer(modifier = Modifier.width(5.dp))
                markdownParse!!.parse(text.substring(text.indexOf('>')+1),state,stateList,recall)()
            }
        }
    }

}