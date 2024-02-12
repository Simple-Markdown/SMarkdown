package indi.midreamsheep.app.tre.ui.filechooser

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.tool.ioc.getBean
import indi.midreamsheep.app.tre.model.setting.settings.store.Store
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import java.io.File

class FileChooserDisplay:Display {
    @Composable
    override fun display() {
        fileChooser(
            Modifier,
            TRELocalFile(File(getBean(Store::class.java).rootPath.getData()))
        )
    }
}