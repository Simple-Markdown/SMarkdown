package indi.midreamsheep.app.tre.desktop.page.setting

import indi.midreamsheep.app.tre.constant.TREPageNameConstant
import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.setting.context.action.SettingGroupAction
import indi.midreamsheep.app.tre.desktop.page.setting.context.viewmodel.SettingGroupViewModel
import indi.midreamsheep.app.tre.desktop.page.setting.ui.settingPage
import indi.midreamsheep.app.tre.service.language.TRELanguageResource
import indi.midreamsheep.app.tre.shared.api.display.Display

class TRESettingWindowContext: TREWindowContext() {
    /* ViewModel */
    val settingGroupViewModel = SettingGroupViewModel(this)

    /* action */
    val settingGroupAction = SettingGroupAction(this)
    override fun getWindowTitle() = TRELanguageResource.getLanguage(
        TREPageNameConstant.SETTING_PAGE_KEY,
        TREPageNameConstant.SETTING_PAGE_DEFAULT
    )!!

    override fun getDisplay() = Display {
        {
            settingPage()
        }
    }
}