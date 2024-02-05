package indi.midreamsheep.app.tre.context.mainpage.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.setting.settings.stroe.Store
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.ui.mainpage.file.FileChooserDisplay
import indi.midreamsheep.app.tre.ui.mainpage.file.fileChooser
import java.io.File

class RightPageViewModel {
    var rightPageViewState : MutableState<Display> = mutableStateOf(FileChooserDisplay())

    fun getRightPageViewState() = rightPageViewState.value
}