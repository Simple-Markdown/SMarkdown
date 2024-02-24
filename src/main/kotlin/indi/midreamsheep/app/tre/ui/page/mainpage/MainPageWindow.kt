package indi.midreamsheep.app.tre.ui.page.mainpage

import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.constant.TREPageNameConstant
import indi.midreamsheep.app.tre.service.language.TRELanguageResource
import indi.midreamsheep.app.tre.service.window.TREWindow

class MainPageWindow :
    TREWindow(
        TRELanguageResource.getLanguage(
            TREPageNameConstant.MAIN_PAGE_KEY,TREPageNameConstant.MAIN_PAGE_DEFAULT
        )
    ) {

    /**
     * 启动窗口
     */
    override fun getDisplay()= Display{
        Window(onCloseRequest = {
            close()
        }, title = name
        ) {
            mainPage()
        }
    }
}