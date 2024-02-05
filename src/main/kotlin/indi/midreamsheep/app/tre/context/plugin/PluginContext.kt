package indi.midreamsheep.app.tre.context.plugin

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.plugin.action.PluginAction
import indi.midreamsheep.app.tre.context.plugin.viewmodel.PluginViewModel

class PluginContext:TREContext {
    val pluginViewModel = PluginViewModel()
    val pluginAction = PluginAction(this)
}