package indi.midreamsheep.app.tre.desktop.page.setting.context.action

import indi.midreamsheep.app.tre.desktop.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.setting.TRESettingWindowContext
import indi.midreamsheep.app.tre.model.setting.settinggroup.TRESettingGroup

class SettingGroupAction(context: TRESettingWindowContext): TREAction<TRESettingWindowContext>(context) {
    fun setCurrentSettingGroup(
        settingGroup: TRESettingGroup
    ) {
        context.settingGroupViewModel.currentSettingGroup.value = settingGroup
    }

    fun saveCurrentSetting() {
        context.settingGroupViewModel.currentSettingGroup.value.save()
    }
}