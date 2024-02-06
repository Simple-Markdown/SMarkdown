package indi.midreamsheep.app.tre.context.setting

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.setting.action.SettingGroupAction
import indi.midreamsheep.app.tre.context.setting.viewmodel.SettingGroupViewModel

class SettingPageContext: TREContext {
    /* ViewModel */
    val settingGroupViewModel = SettingGroupViewModel(this)

    /* action */
    val settingGroupAction = SettingGroupAction(this)
}