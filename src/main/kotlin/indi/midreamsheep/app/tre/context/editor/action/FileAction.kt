package indi.midreamsheep.app.tre.context.editor.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FileAction(context: TREEditorContext): TREAction<TREEditorContext>(context) {
    @OptIn(DelicateCoroutinesApi::class)
    fun store() {
        GlobalScope.launch {
            context.bottomBarAction.setStateString("Saving")
            context.editorFileManager.store()
            context.bottomBarAction.setStateString("Save success")
        }
    }
}