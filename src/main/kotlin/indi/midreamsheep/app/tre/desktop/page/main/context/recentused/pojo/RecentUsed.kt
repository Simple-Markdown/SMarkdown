package indi.midreamsheep.app.tre.desktop.page.main.context.recentused.pojo

import indi.midreamsheep.app.tre.constant.AppPathConstant
import java.io.File

data class RecentUsed(
    val name: String,
    val path: String,
    val time: Long
)

fun readRecentUsed(): List<RecentUsed> {
    val path = AppPathConstant.ROOT_PATH+File.separator+"runtime" + File.separator + "data" + File.separator + "RecentUsed"
    val file = File(path)
    val result = mutableListOf<RecentUsed>()
    //如果文件不存在
    if (!file.exists()) {
        return result
    }
    for (listFile in file.listFiles()!!) {
        if (listFile.isFile) {
            val split = listFile.name.replace(".tre","").split("_")
            if (split.size != 2) {
                continue
            }
            result.add(RecentUsed(split[0], listFile.readText(), split[1].toLong()))
        }
    }
    result.sortByDescending { it.time }
    return result
}