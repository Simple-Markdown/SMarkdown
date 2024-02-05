package indi.midreamsheep.app.tre.model.toolbar.file.tools

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.context.api.annotation.toolbar.toolbars.FileToolBar
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.toolbar.SubBarItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@FileToolBar
class SaveTool: SubBarItem(){
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    override fun call(context: TREEditorContext) {
        GlobalScope.launch {
            context.stateString.value = "Saving"
            context.editorFileManager.store()
            context.stateString.value = "saving success"
        }
    }

    override fun getName(): String {
        return "保存文件"
    }

    override fun getShortcutKey(): String {
        return "Ctrl + S"
    }
}