package indi.midreamsheep.app.tre.model.editor.manager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.model.editor.line.TRELineState

interface TREStateManager {
    fun getMarkdownLineStateList(): SnapshotStateList<TRELineState>
    fun getCurrentMarkdownLine(): TRELineState?
    fun getCurrentMarkdownLineState(): MutableState<TRELineState?>
    fun setCurrentMarkdownLineState(markdownLineState: TRELineState)
}