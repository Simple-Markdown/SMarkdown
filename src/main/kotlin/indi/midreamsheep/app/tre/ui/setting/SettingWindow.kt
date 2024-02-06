package indi.midreamsheep.app.tre.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.ui.app.WindowDisplay
import indi.midreamsheep.app.tre.ui.mainpage.mainPage

class SettingWindow: WindowDisplay() {
    @Composable
    override fun display() {
        Window(onCloseRequest = {
            close?.invoke()
        }) {
            settingPage()
        }
    }
}