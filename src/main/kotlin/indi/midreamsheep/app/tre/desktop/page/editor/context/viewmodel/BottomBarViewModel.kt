package indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel

import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.desktop.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorWindowContext

class BottomBarViewModel(context: TREEditorWindowContext): TREViewModel<TREEditorWindowContext>(context) {
    val stateString = mutableStateOf("file open success")
}