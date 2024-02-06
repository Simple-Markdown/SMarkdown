package indi.midreamsheep.app.tre.context.app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow

class WindowViewModel(context:TREAppContext): TREViewModel<TREAppContext>(context){
    val windows: SnapshotStateList<TREWindow> = mutableStateListOf()
}