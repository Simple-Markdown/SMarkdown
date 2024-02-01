package indi.midreamsheep.app.markdown.model.editor.parser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.model.editor.parser.impl.paragraph.DefaultParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class MarkdownParse {

    @MapInjector(target = "paragraph")
    private val paragraphParser = HashMap<Char,List<ParagraphParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    fun parse(
        text: String,
        state: indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine,
        stateList: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager,
        recall: () -> Unit
    ):@Composable ()->Unit {
        if(text.isEmpty()) {
            return {
                Text(
                    text="",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clickable {
                            recall()
                        }
                )
            }
        }
        val startChar = text[0]
        var parser: ParagraphParser? = null;
        val paragraphParsers = paragraphParser[startChar]
        paragraphParsers?.forEach {
            if (it.formatCheck(text)){
                parser = it
            }
        }
        if (parser==null){
            parser = defaultParser
        }
        return parser!!.getComposable(text,recall,stateList,state)
    }
}