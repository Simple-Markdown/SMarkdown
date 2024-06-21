package indi.midreamsheep.app.tre.desktop.page.home

import indi.midreamsheep.app.tre.constant.TREPageNameConstant
import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.home.context.viewmodel.MainPageButtonViewModel
import indi.midreamsheep.app.tre.desktop.page.home.ui.mainPage
import indi.midreamsheep.app.tre.service.language.TRELanguageResource
import indi.midreamsheep.app.tre.shared.api.display.Display

/**
 * 主页面的上下文
 * */

class TREHomePageWindowContext : TREWindowContext() {
    /** 左侧点击的按钮的管理器 */
    val mainPageButtonViewModel = MainPageButtonViewModel(this)
    override fun getWindowTitle() = TRELanguageResource.getLanguage(
       TREPageNameConstant.MAIN_PAGE_KEY, TREPageNameConstant.MAIN_PAGE_DEFAULT
    )!!

    override fun getDisplay() = Display {
        {
            mainPage()
        }
    }

}