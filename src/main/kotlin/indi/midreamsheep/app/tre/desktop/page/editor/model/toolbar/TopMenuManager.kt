package indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar

import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TopMenuManager {
    @ListInjector(target = "ToolBar")
    var topFloorBarList: List<TopBarItem> = mutableListOf()
}