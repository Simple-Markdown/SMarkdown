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

class TRELocalEditorWindowContext(private val treLocalFile:TRELocalFile): TREWindowContext() {

/*    override fun getWindowContext() = Display {
        {

            val context = remember { TREEditorWindowContext(manager) }
            val treEditorShortcutKeyManager = getBean(TREEditorShortcutKeyManager::class.java)
            context.editorFileManager.getStateManager().setContext(context)
            Window(onCloseRequest = {
                close()
            }, title = name,
                onPreviewKeyEvent = {
                    println("window key")
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
    }*/

    override fun getWindowTitle() = treLocalFile.name!!

    override fun getDisplay() = Display {
        {
            //构建
        }
    }
}

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
                    TRELocalEditorWindowContext(TRELocalFile(File(platformFile.path)))
                ).register()
                close()
            }
        }
    }
}