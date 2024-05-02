package indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel

import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.shared.api.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext

class BottomBarViewModel(context: TREEditorContext): TREViewModel<TREEditorContext>(context) {
    val stateString = mutableStateOf("file open success")
}