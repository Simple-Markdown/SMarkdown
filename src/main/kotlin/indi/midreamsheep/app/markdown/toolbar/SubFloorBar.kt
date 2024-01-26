package indi.midreamsheep.app.markdown.toolbar

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

interface SubFloorBar {
    @Composable
    fun call(manager:MarkdownStateManager)
    fun getName():String
}