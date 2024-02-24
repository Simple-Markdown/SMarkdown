package indi.midreamsheep.app.tre.ui.page.editorpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.service.window.TREWindow
import java.io.File

class OpenFileWindow: TREWindow("") {

    override fun getDisplay() = Display {
        Window(onCloseRequest = {
            close()
        }) {
            var showFilePicker by remember { mutableStateOf(true) }
            FilePicker(show = showFilePicker) { platformFile ->
                if (platformFile == null) {
                    showFilePicker = true
                    close()
                    return@FilePicker
                }
                showFilePicker = false
                TRELocalEditorWindow(TRELocalFile(File(platformFile.path))).register()
                close()
            }
        }
    }
}