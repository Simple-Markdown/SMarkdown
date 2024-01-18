package indi.midreamsheep.app.markdown.editor.parser

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.markdown.editor.manager.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

interface ParagraphParser {
    fun formatCheck(text: String):Boolean
    fun getComposable(text: String, recall: () -> Unit, stateList: MarkdownStateManager, state: MarkdownLineState):@Composable ()->Unit
}