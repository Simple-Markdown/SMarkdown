package indi.midreamsheep.app.tre.model.editor.manager.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class CoreTREStateManager: indi.midreamsheep.app.tre.model.editor.manager.TREStateManager {

    private val markdownLineStateList = mutableStateListOf<indi.midreamsheep.app.tre.model.editor.line.TRELineState>()
    private var currentMarkdownLineState: indi.midreamsheep.app.tre.model.editor.line.TRELineState? = null

    override fun getMarkdownLineStateList(): SnapshotStateList<indi.midreamsheep.app.tre.model.editor.line.TRELineState> {
        return markdownLineStateList
    }

    override fun getCurrentMarkdownLineState(): indi.midreamsheep.app.tre.model.editor.line.TRELineState? {
        return currentMarkdownLineState
    }

    override fun setCurrentMarkdownLineState(markdownLineState: indi.midreamsheep.app.tre.model.editor.line.TRELineState) {
        currentMarkdownLineState = markdownLineState
    }

}