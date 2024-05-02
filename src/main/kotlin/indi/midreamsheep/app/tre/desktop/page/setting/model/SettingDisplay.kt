package indi.midreamsheep.app.tre.desktop.page.setting.model

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.model.setting.setting.TRESettingItem
import indi.midreamsheep.app.tre.shared.api.display.Display

class SettingDisplay(private val configs: Array<TRESettingItem<*>>) : Display {

    override fun getComposable():@Composable () -> Unit {
        return{
                Column {
                    for (config in configs) {
                        config.display.getComposable().invoke()
                    }
                }
            }
    }
}