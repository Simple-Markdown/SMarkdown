package indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.desktop.context.TREViewModel
import indi.midreamsheep.app.tre.shared.api.display.Display

/**
 * 弹窗的ViewModel
 * */
class DialogViewModel(context: TREEditorWindowContext): TREViewModel<TREEditorWindowContext>(context) {
    val dialogDisplay:MutableState<Display> = mutableStateOf(Display.None)
    val isDisplay:MutableState<Boolean> = mutableStateOf(false)

    @Composable
    fun displayDialog(){
        if (isDisplay.value){
            dialogDisplay.value.getComposable().invoke()
        }
    }
}