package indi.midreamsheep.app.tre.ui.filechooser

import indi.midreamsheep.app.tre.api.Recall
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.context.mainpage.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.ui.editor.LocalEditorWindow

class FileOpenRecall :Recall<TREFile> {
    override fun recall(
        file:TREFile
    ) {
        val localFile = (file as TRELocalFile).file
        val recentUsed = RecentUsed(
            name = localFile.name,
            path = localFile.absolutePath,
            time = System.currentTimeMillis()
        )
        TREAppContext.context.recentFileAction.addRecentFile(recentUsed)
        TREAppContext.context.windowAction.registerWindow(TREWindow(LocalEditorWindow(file),"file editor"))
    }

}