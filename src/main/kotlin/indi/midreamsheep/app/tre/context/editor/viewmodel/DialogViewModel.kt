package indi.midreamsheep.app.tre.context.editor.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

/**
 * 弹窗的ViewModel
 * */
class DialogViewModel(context: TREEditorContext): TREViewModel<TREEditorContext>(context) {
    val dialogDisplay:MutableState<Display> = mutableStateOf(Display.None)
    val isDisplay:MutableState<Boolean> = mutableStateOf(false)

    @Composable
    fun displayDialog(){
        if (isDisplay.value){
            dialogDisplay.value.display()
        }
    }
}