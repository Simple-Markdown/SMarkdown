package indi.midreamsheep.app.tre.ui.mainpage.file

import indi.midreamsheep.app.tre.api.Recall
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.ui.editor.LocalEditorWindow

class FileOpenRecall :Recall<TREFile> {
    override fun recall(
        value:TREFile
    ) {
        TREAppContext.context.windowAction.registerWindow(TREWindow(LocalEditorWindow(value),"file editor"))
    }

}