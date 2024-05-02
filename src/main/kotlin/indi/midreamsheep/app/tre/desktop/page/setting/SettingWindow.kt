package indi.midreamsheep.app.tre.desktop.page.setting

import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.constant.TREPageNameConstant
import indi.midreamsheep.app.tre.constant.TREPageNameConstant.MAIN_PAGE_DEFAULT
import indi.midreamsheep.app.tre.desktop.page.main.MainPageWindow
import indi.midreamsheep.app.tre.desktop.page.setting.ui.settingPage
import indi.midreamsheep.app.tre.service.language.TRELanguageResource
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.shared.api.display.Display

class SettingWindow: TREWindow(
    TRELanguageResource.getLanguage(
        TREPageNameConstant.SETTING_PAGE_KEY,
        TREPageNameConstant.SETTING_PAGE_DEFAULT
    )
) {

    override fun getDisplay() = Display {
        val returnMainPage = {
            close()
            val indexOf = treDesktopWindowService.indexOf(TRELanguageResource.getLanguage(
                TREPageNameConstant.MAIN_PAGE_KEY,
                MAIN_PAGE_DEFAULT
            ))
            if (indexOf==-1) {
                //注册主页面
                MainPageWindow().register()
            }
        }
        {
            Window(
                onCloseRequest = returnMainPage,
                title = name
            ) {
                settingPage()
            }
        }
    }
}