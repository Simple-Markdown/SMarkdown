package indi.midreamsheep.app.tre.ui.page.editorpage

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.context.mainpage.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.model.editor.manager.core.render.TRELocalFileRenderManager
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyManager
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.tool.ioc.getBean
import logger

class TRELocalEditorWindow(private val treLocalFile:TRELocalFile): TREWindow(treLocalFile.name) {

    override fun getDisplay() = Display {
        val manager = TRELocalFileRenderManager(treLocalFile)
        val context = remember { TREEditorContext(manager) }
        context.editorFileManager.getStateManager().setContext(context)
        Window(onCloseRequest = {
            close()
        }, title = name,
            onPreviewKeyEvent = {
                return@Window getBean(TREEditorShortcutKeyManager::class.java).keyEvent(it,context)
            }
        ) {
            logger.info("open file: ${treLocalFile.name}")
            editorPage(context)
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
