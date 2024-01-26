package indi.midreamsheep.app.markdown.editor.manager.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.markdown.editor.line.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

class CoreMarkdownStateManager: MarkdownStateManager {

    private val markdownLineStateList = mutableStateListOf<MarkdownLineState>()
    override fun getMarkdownLineStateList(): SnapshotStateList<MarkdownLineState> {
        return markdownLineStateList
    }
}