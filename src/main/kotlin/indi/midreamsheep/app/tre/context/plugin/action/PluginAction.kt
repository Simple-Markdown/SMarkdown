package indi.midreamsheep.app.tre.context.plugin.action

import cn.hutool.json.JSONUtil
import indi.midreamsheep.app.tre.constant.AppPathConstant
import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.plugin.TREPluginContext
import indi.midreamsheep.app.tre.service.ioc.di.scan.PluginScannerTool
import java.io.File

class PluginAction(context: TREPluginContext): TREAction<TREPluginContext>(context) {
    fun save() {
        val loadingPlugins:MutableList<String> = mutableListOf()
        for (plugin in context.pluginViewModel.getPlugins()) {
            if (plugin.isClicked.value) {
                loadingPlugins.add(plugin.fileName!!)
            }
        }
        val pluginConfig = PluginScannerTool.PluginConfig()
        pluginConfig.name = loadingPlugins
        File(AppPathConstant.ROOT_PATH + "/plugins/" + "plugin-config.json").writeText(JSONUtil.toJsonStr(pluginConfig))
    }

}