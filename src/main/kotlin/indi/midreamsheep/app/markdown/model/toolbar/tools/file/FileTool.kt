package indi.midreamsheep.app.markdown.model.toolbar.tools.file

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.model.toolbar.SubBarItem
import indi.midreamsheep.app.markdown.model.toolbar.TopBarItem
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
@ListInjector(target = "topFloorBarList")
class FileTool : TopBarItem() {

    @ListInjector(target = "systemTools" )
    private val subBarList = mutableListOf<SubBarItem>()

    override fun getName(): String {
        return "File"
    }

    override fun getSubBarList(): List<SubBarItem> {
        return subBarList
    }
}