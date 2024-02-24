package indi.midreamsheep.app.tre.context.plugin.viewmodel

import cn.hutool.json.JSONUtil
import indi.midreamsheep.app.tre.constant.ProjectPathConstant
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.plugin.TREPluginContext
import indi.midreamsheep.app.tre.context.plugin.viewmodel.pojo.Plugin
import indi.midreamsheep.app.tre.service.ioc.di.scan.PluginScannerTool
import java.io.File

class PluginViewModel(context: TREPluginContext) : TREViewModel<TREPluginContext>(context) {

    private val plugins = scanPlugins()

    fun getPlugins() = plugins

    private fun scanPlugins(): List<Plugin> {
        val list:MutableList<Plugin> = mutableListOf()
        val rootPath = ProjectPathConstant.ROOT_PATH+ File.separator+"plugins"+ File.separator

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