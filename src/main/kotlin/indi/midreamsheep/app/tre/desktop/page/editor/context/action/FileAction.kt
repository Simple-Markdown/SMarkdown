package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.desktop.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorWindowContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FileAction(context: TREEditorWindowContext): TREAction<TREEditorWindowContext>(context) {
    @OptIn(DelicateCoroutinesApi::class)
    fun store() {
        GlobalScope.launch {
            context.bottomBarAction.setStateString("Saving")
            context.editorFileManager.store()
            context.bottomBarAction.setStateString("Save success")
        }
    }
}