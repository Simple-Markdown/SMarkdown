package indi.midreamsheep.app.tre.desktop.page.setting.context.action

import indi.midreamsheep.app.tre.shared.api.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.setting.context.SettingPageContext
import indi.midreamsheep.app.tre.model.setting.settinggroup.TRESettingGroup

class SettingGroupAction(context: SettingPageContext): TREAction<SettingPageContext>(context) {
    fun setCurrentSettingGroup(
        settingGroup: TRESettingGroup
    ) {
        context.settingGroupViewModel.currentSettingGroup.value = settingGroup
    }

    fun saveCurrentSetting() {
        context.settingGroupViewModel.currentSettingGroup.value.save()
    }
}