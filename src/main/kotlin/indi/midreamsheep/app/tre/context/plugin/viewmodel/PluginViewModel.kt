package indi.midreamsheep.app.tre.context.plugin.viewmodel

import cn.hutool.json.JSONUtil
import indi.midreamsheep.app.tre.context.di.scan.PluginScannerTool
import indi.midreamsheep.app.tre.context.plugin.viewmodel.pojo.Plugin
import java.io.File

class PluginViewModel {

    private val plugins = scanPlugins()

    fun getPlugins() = plugins

    private fun scanPlugins(): List<Plugin> {
        val list:MutableList<Plugin> = mutableListOf()
        val rootPath = System.getProperty("user.dir")+ File.separator+"plugins"+ File.separator

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
}