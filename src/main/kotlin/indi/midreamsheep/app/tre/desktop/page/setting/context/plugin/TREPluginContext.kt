package indi.midreamsheep.app.tre.desktop.page.setting.context.plugin

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.action.PluginAction
import indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.viewmodel.PluginViewModel

class TREPluginContext: TREWindowContext {
    val pluginViewModel = PluginViewModel(this)
    val pluginAction = PluginAction(this)
}