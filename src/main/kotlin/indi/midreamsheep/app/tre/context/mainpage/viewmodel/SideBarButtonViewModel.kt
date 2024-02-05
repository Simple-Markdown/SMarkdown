package indi.midreamsheep.app.tre.context.mainpage.viewmodel

import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButtonManager

class SideBarButtonViewModel {
    private var buttonsManager = getBean(TREMainPageButtonManager::class.java)
    fun getButtons(): MutableList<TREMainPageButton> = buttonsManager.buttons
}