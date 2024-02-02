package indi.midreamsheep.app.markdown.model.toolbar.system

import indi.midreamsheep.app.markdown.context.api.annotation.toolbar.ToolBar
import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.model.toolbar.SubBarItem
import indi.midreamsheep.app.markdown.model.toolbar.TopBarItem
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

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