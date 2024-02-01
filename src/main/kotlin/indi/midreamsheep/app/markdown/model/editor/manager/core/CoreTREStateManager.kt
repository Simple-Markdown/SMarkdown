package indi.midreamsheep.app.markdown.model.editor.manager.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.markdown.model.editor.line.TRELineState
import indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager

class CoreTREStateManager: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager {

    private val markdownLineStateList = mutableStateListOf<indi.midreamsheep.app.markdown.model.editor.line.TRELineState>()
    private var currentMarkdownLineState: indi.midreamsheep.app.markdown.model.editor.line.TRELineState? = null

    override fun getMarkdownLineStateList(): SnapshotStateList<indi.midreamsheep.app.markdown.model.editor.line.TRELineState> {
        return markdownLineStateList
    }

    override fun getCurrentMarkdownLineState(): indi.midreamsheep.app.markdown.model.editor.line.TRELineState? {
        return currentMarkdownLineState
    }

    override fun setCurrentMarkdownLineState(markdownLineState: indi.midreamsheep.app.markdown.model.editor.line.TRELineState) {
        currentMarkdownLineState = markdownLineState
    }

}