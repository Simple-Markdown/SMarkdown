package indi.midreamsheep.app.markdown.model.editor.manager

import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.markdown.model.editor.line.TRELineState

interface TREStateManager {
    fun getMarkdownLineStateList(): SnapshotStateList<indi.midreamsheep.app.markdown.model.editor.line.TRELineState>
    fun getCurrentMarkdownLineState(): indi.midreamsheep.app.markdown.model.editor.line.TRELineState?
    fun setCurrentMarkdownLineState(markdownLineState: indi.midreamsheep.app.markdown.model.editor.line.TRELineState)
}