package indi.midreamsheep.app.tre.context.setting.viewmodel

import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.TREViewModel
import indi.midreamsheep.app.tre.context.setting.SettingPageContext
import indi.midreamsheep.app.tre.model.setting.settinggroup.TRESettingGroup
import indi.midreamsheep.app.tre.model.setting.settinggroup.SettingGroupManager

class SettingGroupViewModel(context: SettingPageContext): TREViewModel<SettingPageContext>(context) {

    private val settingGroupManager = getBean(SettingGroupManager::class.java)
    private val settingGroups = settingGroupManager.settingGroups/* 插件 */

    val currentSettingGroup = mutableStateOf(settingGroups[0])
    fun getSettingGroupList(): MutableList<TRESettingGroup> = settingGroups
}