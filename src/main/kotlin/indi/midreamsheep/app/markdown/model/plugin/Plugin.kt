package indi.midreamsheep.app.markdown.model.plugin

import androidx.compose.runtime.mutableStateOf
import cn.hutool.json.JSONUtil
import indi.midreamsheep.app.markdown.context.di.scan.PluginScannerTool
import java.io.File

class Plugin {
    var name: String? = null
    var fileName: String? = null
    var version: String? = null
    var description: String? = null
    var author: String? = null
    var url: String? = null
    var isClicked = mutableStateOf(false)
}

fun getPluginJson(plugin: Plugin): String {
    return "{" +
            "\"name\":\"" + plugin.name + "\"" +
            ",\"version\":\"" + plugin.version + "\"" +
            ",\"description\":\"" + plugin.description + "\"" +
            ",\"author\":\"" + plugin.author + "\"" +
            ",\"url\":\"" + plugin.url + "\"" +
            "}"
}


fun scanPlugins(): List<Plugin> {
    val list:MutableList<Plugin> = mutableListOf()
    val rootPath = System.getProperty("user.dir")+ File.separator+"plugins"+File.separator

    val configFile = File(rootPath + "plugin-config.json")
    if (!configFile.exists()) {
        configFile.writeText(JSONUtil.toJsonStr(PluginScannerTool.PluginConfig()))
        return list
    }
    val configs = JSONUtil.toBean(configFile.readText(), PluginScannerTool.PluginConfig::class.java)!!.name

    for (listFile in File(rootPath).listFiles()!!) {
        if(!listFile.isDirectory){
            continue
        }
        val readText = File(listFile, "plugin.json").readText()
        val plugin = JSONUtil.toBean(readText, Plugin::class.java)
        plugin.fileName = listFile.name
        if (configs.contains(plugin!!.fileName)) {
            plugin.isClicked.value = true
        }
        list.add(plugin)
    }
    return list
}