package indi.midreamsheep.app.tre.desktop.app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.shared.api.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.app.TREAppContext
import indi.midreamsheep.app.tre.desktop.page.main.context.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.desktop.page.main.context.recentused.pojo.readRecentUsed

class RecentFileViewModel(context: TREAppContext): TREViewModel<TREAppContext>(context) {

    val recentFileList:SnapshotStateList<RecentUsed> = mutableStateListOf()

    init {
        val readRecentUsed = readRecentUsed()
        recentFileList.addAll(readRecentUsed)
    }
}