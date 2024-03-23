package indi.midreamsheep.app.tre.model.editor.manager.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager

class CoreTREStateManager: TREStateManager {

    private lateinit var context: TREEditorContext
    private val markdownLineStateList = mutableStateListOf<TREBlockState>()
    private var currentMarkdownLineState: MutableState<TREBlockState?> = mutableStateOf(null)

    override fun getTREBlockStateList(): SnapshotStateList<TREBlockState> {
        return markdownLineStateList
    }

    override fun getCurrentBlockState(): MutableState<TREBlockState?> {
        return currentMarkdownLineState
    }

    override fun getCurrentBlock(): TREBlockState? {
        return currentMarkdownLineState.value
    }

    override fun setCurrentBlockState(markdownLineState: TREBlockState?) {
        currentMarkdownLineState.value = markdownLineState
    }

    override fun getContext() = context
    override fun setContext(context: TREEditorContext) {
        this.context = context
    }


}