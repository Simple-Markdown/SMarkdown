package indi.midreamsheep.app.tre.context.plugin

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.plugin.action.PluginAction
import indi.midreamsheep.app.tre.context.plugin.viewmodel.PluginViewModel

class TREPluginContext:TREContext {
    val pluginViewModel = PluginViewModel(this)
    val pluginAction = PluginAction(this)
}