package indi.midreamsheep.app.tre.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.service.language.TRELanguageResource
import indi.midreamsheep.app.tre.ui.app.WindowDisplay

class SettingWindow: WindowDisplay() {
    @Composable
    override fun display() {
        Window(
            onCloseRequest = {
            close?.invoke()
             },
            title = TRELanguageResource.getLanguage("SettingTitle","settings")
        ) {
            settingPage()
        }
    }
}