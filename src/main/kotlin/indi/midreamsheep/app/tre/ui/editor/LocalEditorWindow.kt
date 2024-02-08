package indi.midreamsheep.app.tre.ui.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.model.editor.manager.core.TRELocalFileManager
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.ui.app.WindowDisplay
import logger

class LocalEditorWindow(private val file:TREFile): WindowDisplay() {
    @Composable
    override fun display() {
        Window(onCloseRequest = {
            close?.invoke()
        }) {
            logger.info("open file: ${file.name}")
            editorPage(TRELocalFileManager(file))
        }
    }
}