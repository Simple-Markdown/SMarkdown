package indi.midreamsheep.app.tre.model.editor.manager

import androidx.compose.runtime.snapshots.SnapshotStateList

interface TREStateManager {
    fun getMarkdownLineStateList(): SnapshotStateList<indi.midreamsheep.app.tre.model.editor.line.TRELineState>
    fun getCurrentMarkdownLineState(): indi.midreamsheep.app.tre.model.editor.line.TRELineState?
    fun setCurrentMarkdownLineState(markdownLineState: indi.midreamsheep.app.tre.model.editor.line.TRELineState)
}