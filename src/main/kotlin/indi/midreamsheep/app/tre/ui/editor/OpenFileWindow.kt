package indi.midreamsheep.app.tre.ui.editor

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.ui.app.WindowDisplay
import java.io.File

class OpenFileWindow: WindowDisplay() {
    @Composable
    override fun display() {
        Window(onCloseRequest = {
            close?.invoke()
        }) {
            var showFilePicker by remember { mutableStateOf(true) }
            FilePicker(show = showFilePicker) { platformFile ->
                if (platformFile == null) {
                    showFilePicker = true
                    close?.invoke()
                    return@FilePicker
                }
                showFilePicker = false
                val file = TRELocalFile(File(platformFile.path))
                TREAppContext.context.windowAction.registerWindow(
                    TREWindow(
                        name = "file",
                        windowDisplay = LocalEditorWindow(file)
                    )
                )
                close?.invoke()
            }
        }
    }
}