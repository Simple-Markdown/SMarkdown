package indi.midreamsheep.app.tre.desktop.page.editor

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import indi.midreamsheep.app.tre.desktop.app.TREAppContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.desktop.page.editor.model.listener.shortcut.handler.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.desktop.page.editor.ui.editorPage
import indi.midreamsheep.app.tre.desktop.page.main.context.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.manager.core.render.TRELocalFileRenderManager
import logger
import java.io.File


class TRELocalEditorWindow(private val treLocalFile:TRELocalFile): TREWindow(treLocalFile.name) {

    companion object {
        val LocalContext = compositionLocalOf<TREEditorContext> { error("context is null") }
    }

    override fun getDisplay() = Display {
        {
            val manager = TRELocalFileRenderManager(treLocalFile)
            val context = remember { TREEditorContext(manager) }
            val treEditorShortcutKeyManager = getBean(TREEditorShortcutKeyManager::class.java)
            context.editorFileManager.getStateManager().setContext(context)
            Window(onCloseRequest = {
                close()
            }, title = name,
                onPreviewKeyEvent = {
                    return@Window treEditorShortcutKeyManager.keyEvent(it,context)
                }
            ) {
                context.window = this.window
                logger.info("open file: ${treLocalFile.name}")
                CompositionLocalProvider(LocalContext provides context){
                    editorPage()
                }
            }
        }
    }

    override fun register() {
        val file = treLocalFile.file
        val recentUsed = RecentUsed(
            name = file.name,
            path = file.absolutePath,
            time = System.currentTimeMillis()
        )
        TREAppContext.context.recentFileAction.addRecentFile(recentUsed)
        super.register()
    }
}

class OpenFileWindow: TREWindow("choose file") {

    override fun getDisplay() = Display {
        {
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
}