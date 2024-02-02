package indi.midreamsheep.app.markdown.model.main.pages

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREMainPageButtonManager {
    @ListInjector(target = "mainPageButtons")
    val buttons: List<TREMainPageButton> = mutableListOf()
}