package indi.midreamsheep.app.tre.context.app.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.mainpage.recentused.pojo.RecentUsed
import java.io.File

class RecentFileAction(context:TREAppContext): TREAction<TREAppContext>(context) {
    fun addRecentFile(
        recentFile: RecentUsed
    ) {
        val recentFileList = context.recentFileViewModel.recentFileList
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
        val recentFileList = context.recentFileViewModel.recentFileList
        //通过路径删除
        for (recentUsed in recentFileList) {
            if (recentUsed.path == recentFile.path) {
                recentFileList.remove(recentUsed)
                break
            }
        }
        updateRecentFile()
    }

    private fun updateRecentFile() {
        val recentFileList = context.recentFileViewModel.recentFileList
        val path = System.getProperty("user.dir")+File.separator+"runtime" + File.separator + "data" + File.separator + "RecentUsed"
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
}