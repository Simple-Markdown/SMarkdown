package indi.midreamsheep.app.markdown.model.editor.parser

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

interface SpanParser {
    fun formatCheck(text: String): Boolean
    /**
     *
     * */
    fun generateAnnotatedString(text: String): Triple<Int,AnnotatedString,List<@Composable ()->Unit>>
}