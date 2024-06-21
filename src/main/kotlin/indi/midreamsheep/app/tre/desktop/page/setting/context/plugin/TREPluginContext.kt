package indi.midreamsheep.app.tre.desktop.page.setting.context.plugin

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.action.PluginAction
import indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.viewmodel.PluginViewModel
import indi.midreamsheep.app.tre.shared.api.display.Display

class TREPluginContext: TREWindowContext() {
    val pluginViewModel = PluginViewModel(this)
    val pluginAction = PluginAction(this)
    override fun getWindowTitle(): String {
        TODO("Not yet implemented")
    }

    override fun getDisplay(): Display {
        TODO("Not yet implemented")
    }
}