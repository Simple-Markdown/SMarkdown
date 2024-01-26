package indi.midreamsheep.app.markdown.editor.line

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

interface MarkdownLineInter {
    fun focus():Boolean
    fun getComposable(markdownLineStateManager: MarkdownStateManager):@Composable ()->Unit
    fun getMarkdownContent():String
}