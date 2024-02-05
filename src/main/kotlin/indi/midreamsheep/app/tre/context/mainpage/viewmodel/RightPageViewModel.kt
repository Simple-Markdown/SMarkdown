package indi.midreamsheep.app.tre.context.mainpage.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.ui.mainpage.file.FileChooserDisplay

class RightPageViewModel {
    var rightPageViewState : MutableState<Display> = mutableStateOf(FileChooserDisplay())
    fun getRightPageViewState() = rightPageViewState.value
}