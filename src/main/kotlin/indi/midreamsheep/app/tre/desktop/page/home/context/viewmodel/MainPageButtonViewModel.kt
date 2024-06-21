package indi.midreamsheep.app.tre.desktop.page.home.context.viewmodel

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.desktop.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.page.home.TREHomePageWindowContext
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButtonManager

class MainPageButtonViewModel(context: TREHomePageWindowContext): TREViewModel<TREHomePageWindowContext>(context) {
    private var buttonsManager = getBean(TREMainPageButtonManager::class.java)
    fun getButtons(): MutableList<TREMainPageButton> = buttonsManager.buttons
}