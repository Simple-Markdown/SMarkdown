package indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.file

import indi.midreamsheep.app.tre.api.annotation.toolbar.ToolBar
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.SubBarItem
import indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.TopBarItem

@ToolBar
class FileTool : TopBarItem() {

    @ListInjector(target = "FileToolBar")
    private val subBarList = mutableListOf<SubBarItem>()

    override fun getName(): String {
        return "File"
    }

    override fun getSubBarList(): List<SubBarItem> {
        return subBarList
    }
}