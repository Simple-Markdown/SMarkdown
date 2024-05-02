package indi.midreamsheep.app.tre.desktop.page.main.context.viewmodel

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.shared.api.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.page.main.context.TREMainPageContext
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButtonManager

class MainPageButtonViewModel(context: TREMainPageContext): TREViewModel<TREMainPageContext>(context) {
    private var buttonsManager = getBean(TREMainPageButtonManager::class.java)
    fun getButtons(): MutableList<TREMainPageButton> = buttonsManager.buttons
}