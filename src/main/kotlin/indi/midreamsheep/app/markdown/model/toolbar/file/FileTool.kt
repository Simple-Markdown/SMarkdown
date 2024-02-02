package indi.midreamsheep.app.markdown.model.toolbar.file

import indi.midreamsheep.app.markdown.context.api.annotation.toolbar.ToolBar
import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.model.toolbar.SubBarItem
import indi.midreamsheep.app.markdown.model.toolbar.TopBarItem

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