package indi.midreamsheep.app.tre.model.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.model.setting.setting.TRESettingItem

class SettingDisplay(private val configs: Array<TRESettingItem<*>>) :Display {
    @Composable
    override fun display() {
        Column {
            for (config in configs) {
                config.display.display()
            }
        }
    }
}