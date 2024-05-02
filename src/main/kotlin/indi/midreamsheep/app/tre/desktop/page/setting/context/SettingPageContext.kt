package indi.midreamsheep.app.tre.desktop.page.setting.context

import indi.midreamsheep.app.tre.shared.api.context.TREContext
import indi.midreamsheep.app.tre.desktop.page.setting.context.action.SettingGroupAction
import indi.midreamsheep.app.tre.desktop.page.setting.context.viewmodel.SettingGroupViewModel

class SettingPageContext: TREContext {
    /* ViewModel */
    val settingGroupViewModel = SettingGroupViewModel(this)

    /* action */
    val settingGroupAction = SettingGroupAction(this)
}