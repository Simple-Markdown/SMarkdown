package indi.midreamsheep.app.markdown.editor.manager.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.markdown.editor.line.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

class CoreMarkdownStateManager: MarkdownStateManager {

    private val markdownLineStateList = mutableStateListOf<MarkdownLineState>()
    private var currentMarkdownLineState: MarkdownLineState? = null

    override fun getMarkdownLineStateList(): SnapshotStateList<MarkdownLineState> {
        return markdownLineStateList
    }

    override fun getCurrentMarkdownLineState(): MarkdownLineState? {
        return currentMarkdownLineState
    }

    override fun setCurrentMarkdownLineState(markdownLineState: MarkdownLineState) {
        currentMarkdownLineState = markdownLineState
    }

}