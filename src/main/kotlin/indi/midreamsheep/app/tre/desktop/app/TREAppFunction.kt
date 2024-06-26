package indi.midreamsheep.app.tre.desktop.app

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import indi.midreamsheep.app.tre.constant.AppPathConstant
import indi.midreamsheep.app.tre.desktop.page.home.context.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.desktop.page.home.context.recentused.pojo.readRecentUsed
import java.io.File

val recentFileList: SnapshotStateList<RecentUsed> = mutableStateListOf<RecentUsed>().apply {
    addAll(readRecentUsed())
}


fun addRecentFile(
    recentFile: RecentUsed
) {
    for (recentUsed in recentFileList) {
        if (recentUsed.path == recentFile.path) {
            recentFileList.remove(recentUsed)
            break
        }
    }
    recentFileList.add(0, recentFile)
    updateRecentFile()
}

fun removeRecentFile(
    recentFile: RecentUsed
) {
    //通过路径删除
    for (recentUsed in recentFileList) {
        if (recentUsed.path == recentFile.path) {
            recentFileList.remove(recentUsed)
            break
        }
    }
    updateRecentFile()
}

@Suppress("NAME_SHADOWING")
private fun updateRecentFile() {
    val path = AppPathConstant.ROOT_PATH+ File.separator+"runtime" + File.separator + "data" + File.separator + "RecentUsed"
    val file = File(path)
    if (!file.exists()) {
        file.mkdirs()
    }
    for (listFile in file.listFiles()!!) {
        listFile.delete()
    }
    for (recentUsed in recentFileList) {
        val file = File(path + File.separator + recentUsed.name + "_" + recentUsed.time+".tre")
        file.createNewFile()
        file.writeText(recentUsed.path)
    }
    //排序
    recentFileList.sortByDescending { it.time }
    while (recentFileList.size > 10) {
        removeRecentFile(recentFileList.last())
    }
}