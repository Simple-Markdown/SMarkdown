package indi.midreamsheep.app.markdown.model.editor.parser.impl.paragraph

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager
import indi.midreamsheep.app.markdown.model.editor.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@MapInjector(target = "paragraph",key="-")
@Comment
class DivideLineParser: ParagraphParser {
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

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager,
        state: indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
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