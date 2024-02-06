package indi.midreamsheep.app.tre.context.setting.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.setting.SettingPageContext
import indi.midreamsheep.app.tre.model.setting.settinggroup.SettingGroup

class SettingGroupAction(context: SettingPageContext): TREAction<SettingPageContext>(context) {
    fun setCurrentSettingGroup(
        settingGroup: SettingGroup
    ) {
        context.settingGroupViewModel.currentSettingGroup.value = settingGroup
    }
}