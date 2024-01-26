package indi.midreamsheep.app.markdown.editor.manager

import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.markdown.editor.line.MarkdownLineState

interface MarkdownStateManager {
    fun getMarkdownLineStateList(): SnapshotStateList<MarkdownLineState>
}