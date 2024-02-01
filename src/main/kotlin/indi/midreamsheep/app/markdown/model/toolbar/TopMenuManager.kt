package indi.midreamsheep.app.markdown.model.toolbar

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TopMenuManager {
    @ListInjector(target = "topFloorBarList")
    var topFloorBarList: List<TopBarItem> = mutableListOf()
}