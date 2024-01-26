package indi.midreamsheep.app.markdown.toolbar.tools.system

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.toolbar.SubFloorBar
import indi.midreamsheep.app.markdown.toolbar.TopFloorBar
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
@ListInjector(target = "topFloorBarList")
class SystemTool: TopFloorBar() {

    @ListInjector(target = "systemTools" )
    private val subBarList = mutableListOf<SubFloorBar>()

    override fun getName(): String {
        return "File"
    }

    override fun getSubBarList(): List<SubFloorBar> {
        return subBarList
    }
}