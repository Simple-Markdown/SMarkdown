package indi.midreamsheep.app.tre.context.editor.viewmodel

import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class BottomBarViewModel(treEditorContext: TREEditorContext) {
    val stateString = mutableStateOf("file open success")
}