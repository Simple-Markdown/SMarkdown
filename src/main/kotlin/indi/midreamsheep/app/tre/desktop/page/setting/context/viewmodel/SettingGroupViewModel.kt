package indi.midreamsheep.app.tre.desktop.page.setting.context.viewmodel

import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.desktop.context.TREViewModel
import indi.midreamsheep.app.tre.desktop.page.setting.TRESettingWindowContext
import indi.midreamsheep.app.tre.model.setting.settinggroup.TRESettingGroup
import indi.midreamsheep.app.tre.model.setting.settinggroup.SettingGroupManager

class SettingGroupViewModel(context: TRESettingWindowContext): TREViewModel<TRESettingWindowContext>(context) {

    private val settingGroupManager = getBean(SettingGroupManager::class.java)
    private val settingGroups = settingGroupManager.settingGroups/* 插件 */

    val currentSettingGroup = mutableStateOf(settingGroups[0])
    fun getSettingGroupList(): MutableList<TRESettingGroup> = settingGroups
}