package indi.midreamsheep.app.markdown.model.editor.parser.impl.span

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import indi.midreamsheep.app.markdown.model.editor.parser.SpanParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
//@MapInjector(target="span",key="a")
class ImageParser : SpanParser {
    override fun formatCheck(text: String): Boolean {
        return true
    }

    /**
     *
     * */
    override fun generateAnnotatedString(text: String): Triple<Int, AnnotatedString, List<@Composable () -> Unit>> {
        return Triple(1, AnnotatedString(""), listOf {
            Image(
                painter = painterResource("bg.jpg"),
                contentDescription = null
            )
        })
    }
}