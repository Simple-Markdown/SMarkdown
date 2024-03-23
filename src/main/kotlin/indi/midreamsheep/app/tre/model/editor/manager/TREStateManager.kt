package indi.midreamsheep.app.tre.model.editor.manager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState

interface TREStateManager {
    fun getTREBlockStateList(): SnapshotStateList<TREBlockState>
    fun getCurrentBlock(): TREBlockState?
    fun getCurrentBlockState(): MutableState<TREBlockState?>
    fun setCurrentBlockState(markdownLineState: TREBlockState?)
    fun getContext(): TREEditorContext
    fun setContext(context: TREEditorContext)
}