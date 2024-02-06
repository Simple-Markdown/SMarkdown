package indi.midreamsheep.app.tre.context.editor.viewmodel

import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class BottomBarViewModel(context: TREEditorContext): TREViewModel<TREEditorContext>(context) {
    val stateString = mutableStateOf("file open success")
}