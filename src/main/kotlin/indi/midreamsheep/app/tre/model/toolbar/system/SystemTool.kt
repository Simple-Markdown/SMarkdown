package indi.midreamsheep.app.tre.model.toolbar.system

import indi.midreamsheep.app.tre.api.annotation.toolbar.ToolBar
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.tre.model.toolbar.SubBarItem
import indi.midreamsheep.app.tre.model.toolbar.TopBarItem

@ToolBar
class SystemTool: TopBarItem() {

    @ListInjector(target = "EditorToolBar" )
    private val subBarList = mutableListOf<SubBarItem>()

    override fun getName(): String {
        return "editor"
    }

    override fun getSubBarList(): List<SubBarItem> {
        return subBarList
    }
}