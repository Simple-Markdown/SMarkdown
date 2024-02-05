package indi.midreamsheep.app.tre.ui.mainpage.file

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.setting.settings.stroe.Store
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