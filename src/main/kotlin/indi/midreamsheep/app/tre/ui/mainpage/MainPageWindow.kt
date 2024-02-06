package indi.midreamsheep.app.tre.ui.mainpage

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.ui.app.WindowDisplay

class MainPageWindow: WindowDisplay() {
    @Composable
    override fun display() {
        Window(onCloseRequest = {
            close?.invoke()
        }) {
            mainPage()
        }
    }
}