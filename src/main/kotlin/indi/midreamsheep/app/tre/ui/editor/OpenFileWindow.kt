package indi.midreamsheep.app.tre.ui.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.setting.settings.stroe.Store
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.ui.app.WindowDisplay
import indi.midreamsheep.app.tre.ui.mainpage.file.fileChooser
import java.io.File

class OpenFileWindow: WindowDisplay() {
    @Composable
    override fun display() {
        Window(onCloseRequest = {
            close?.invoke()
        }) {
            fileChooser(
                modifier = Modifier.fillMaxSize(),
                rootFile  = TRELocalFile(File(getBean(Store::class.java).rootPath.data))
            )
        }
    }
}