package indi.midreamsheep.app.tre.desktop.page.main

import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.constant.TREPageNameConstant
import indi.midreamsheep.app.tre.desktop.page.main.ui.mainPage
import indi.midreamsheep.app.tre.service.language.TRELanguageResource
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.shared.api.display.Display

/**
 * 主页面窗口类，用于启动窗口
 * */
class MainPageWindow :
    TREWindow(
        TRELanguageResource.getLanguage(
            TREPageNameConstant.MAIN_PAGE_KEY,TREPageNameConstant.MAIN_PAGE_DEFAULT
        )
    ) {

    override fun getDisplay()= Display{
        {
            Window(onCloseRequest = { close() }, title = name) {
                mainPage()
            }
        }
    }
}