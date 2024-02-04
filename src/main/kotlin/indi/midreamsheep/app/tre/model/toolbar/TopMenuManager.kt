package indi.midreamsheep.app.tre.model.toolbar

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TopMenuManager {
    @ListInjector(target = "ToolBar")
    var topFloorBarList: List<TopBarItem> = mutableListOf()
}