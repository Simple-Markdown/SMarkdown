package indi.midreamsheep.app.tre.model.editor.manager.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.model.editor.line.TRELineState

class CoreTREStateManager: indi.midreamsheep.app.tre.model.editor.manager.TREStateManager {

    private val markdownLineStateList = mutableStateListOf<TRELineState>()
    private var currentMarkdownLineState: MutableState<TRELineState?> = mutableStateOf(null)

    override fun getMarkdownLineStateList(): SnapshotStateList<TRELineState> {
        return markdownLineStateList
    }

    override fun getCurrentMarkdownLineState(): MutableState<TRELineState?> {
        return currentMarkdownLineState
    }

    override fun getCurrentMarkdownLine(): TRELineState? {
        return currentMarkdownLineState.value
    }

    override fun setCurrentMarkdownLineState(markdownLineState: TRELineState) {
        currentMarkdownLineState.value = markdownLineState
    }

}