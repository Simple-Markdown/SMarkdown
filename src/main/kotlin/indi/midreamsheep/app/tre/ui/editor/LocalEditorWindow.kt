package indi.midreamsheep.app.tre.ui.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.context.mainpage.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.model.editor.manager.core.TRELocalFileManager
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.ui.app.WindowDisplay
import logger

class LocalEditorWindow(private val file:TRELocalFile): WindowDisplay() {
    @Composable
    override fun display() {
        Window(onCloseRequest = {
            close?.invoke()
        }, title = file.name
        ) {
            logger.info("open file: ${file.name}")
            editorPage(TRELocalFileManager(file))
        }
    }
}

fun registerLocalEditorWindow(treFile:TRELocalFile) {
    val file = treFile.file
    val recentUsed = RecentUsed(
        name = file.name,
        path = file.absolutePath,
        time = System.currentTimeMillis()
    )
    TREAppContext.context.recentFileAction.addRecentFile(recentUsed)
    TREAppContext.context.windowAction.registerWindow(TREWindow(LocalEditorWindow(treFile),"file editor"))
}