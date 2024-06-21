package indi.midreamsheep.app.tre.desktop.page.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.shared.api.display.Display
import java.io.File

class TREOpenFileWindowContext: TREWindowContext() {

    override fun getWindowTitle() = "Choose File"

    override fun getDisplay() = Display {
        {
            var showFilePicker by remember { mutableStateOf(true) }
            FilePicker(show = showFilePicker) { platformFile ->
                if (platformFile == null) {
                    showFilePicker = true
                    close()
                    return@FilePicker
                }
                showFilePicker = false
                TREWindow(
                    TREEditorWindowContext(TRELocalFile(File(platformFile.path)))
                ).register()
                close()
            }
        }
    }
}