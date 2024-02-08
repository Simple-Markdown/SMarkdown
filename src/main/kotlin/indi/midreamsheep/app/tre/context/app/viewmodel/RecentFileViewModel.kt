package indi.midreamsheep.app.tre.context.app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.mainpage.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.context.mainpage.recentused.pojo.readRecentUsed

class RecentFileViewModel(context: TREAppContext): TREViewModel<TREAppContext>(context) {

    val recentFileList:SnapshotStateList<RecentUsed> = mutableStateListOf()

    init {
        val readRecentUsed = readRecentUsed()
        recentFileList.addAll(readRecentUsed)
    }
}