package indi.midreamsheep.app.markdown.editor.manager

import androidx.compose.runtime.snapshots.SnapshotStateList

interface MarkdownStateManager {
    fun getMarkdownLineStateList(): SnapshotStateList<MarkdownLineState>
    fun getNowFocusedLine(): Int
    fun setNowFocusedLine(line: Int)
}