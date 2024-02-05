package indi.midreamsheep.app.tre.context.plugin.action

import cn.hutool.json.JSONUtil
import indi.midreamsheep.app.tre.context.di.scan.PluginScannerTool
import indi.midreamsheep.app.tre.context.plugin.PluginContext
import java.io.File

class PluginAction(private val pluginContext: PluginContext) {
    fun save() {
        val loadingPlugins:MutableList<String> = mutableListOf()
        for (plugin in pluginContext.pluginViewModel.getPlugins()) {
            if (plugin.isClicked.value) {
                loadingPlugins.add(plugin.fileName!!)
            }
        }
        val pluginConfig = PluginScannerTool.PluginConfig()
        pluginConfig.name = loadingPlugins
        File(System.getProperty("user.dir") + "/plugins/" + "plugin-config.json").writeText(JSONUtil.toJsonStr(pluginConfig))
    }

}