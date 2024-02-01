package indi.midreamsheep.app.markdown.model.toolbar.tools.system

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.model.toolbar.SubBarItem
import indi.midreamsheep.app.markdown.model.toolbar.TopBarItem
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
@ListInjector(target = "topFloorBarList")
class SystemTool: TopBarItem() {

    @ListInjector(target = "systemTools" )
    private val subBarList = mutableListOf<SubBarItem>()

    override fun getName(): String {
        return "editor"
    }

    override fun getSubBarList(): List<SubBarItem> {
        return subBarList
    }
}