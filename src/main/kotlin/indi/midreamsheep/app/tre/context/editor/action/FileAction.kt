package indi.midreamsheep.app.tre.context.editor.action

import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FileAction(private val context: TREEditorContext) {
    @OptIn(DelicateCoroutinesApi::class)
    fun store() {
        GlobalScope.launch {
            context.bottomBarAction.setStateString("Saving")
            context.editorFileManager.store()
            context.bottomBarAction.setStateString("Save success")
        }
    }
}