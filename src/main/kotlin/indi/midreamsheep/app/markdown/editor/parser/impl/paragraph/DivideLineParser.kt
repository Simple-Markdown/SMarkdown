package indi.midreamsheep.app.markdown.editor.parser.impl.paragraph

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.editor.manager.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@MapInjector(target = "paragraph",key="-")
@Comment
class DivideLineParser: ParagraphParser {
    override fun formatCheck(text: String): Boolean {
        if (text.length<3) return false
        if (text[1]!='-'||text[2]!='-'||text[0]!='-') return false
        //如果之后是空格或者换行符，那么就是分割线
        if (text.length==3) return true
        val pointer = 3
        while (pointer<text.length) {
            if (text[pointer] != '-' || text[pointer] != ' ') {
                return false
            }
        }
        return true
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: MarkdownStateManager,
        state: MarkdownLineState
    ):@Composable () -> Unit {
        return {
            //分割线
            Column(
                modifier = Modifier.fillMaxWidth().height(20.dp).clickable { recall() },
                verticalArrangement = Arrangement.Center
            ){
                Divider(
                    color = Color.Gray
                )
            }
        }
    }
}