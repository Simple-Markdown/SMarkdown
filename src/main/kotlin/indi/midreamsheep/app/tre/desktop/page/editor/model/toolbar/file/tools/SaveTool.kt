package indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.file.tools

import indi.midreamsheep.app.tre.api.annotation.toolbar.toolbars.FileToolBar
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.SubBarItem

@FileToolBar
class SaveTool: SubBarItem(){

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