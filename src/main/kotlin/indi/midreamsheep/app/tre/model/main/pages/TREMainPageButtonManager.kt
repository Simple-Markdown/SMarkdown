package indi.midreamsheep.app.tre.model.main.pages

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREMainPageButtonManager {
    @ListInjector(target = "mainPageButtons")
    val buttons: List<TREMainPageButton> = mutableListOf()
}