package indi.midreamsheep.app.tre.model.toolbar.file.tools

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.api.annotation.toolbar.toolbars.FileToolBar
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.toolbar.SubBarItem

@FileToolBar
class SaveTool: SubBarItem(){
    @Composable
    override fun call(context: TREEditorContext) {
        context.fileAction.store()
    }

    override fun getName(): String {
        return "保存文件"
    }

    override fun getShortcutKey(): String {
        return "Ctrl + S"
    }
}