package indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.system

import indi.midreamsheep.app.tre.api.annotation.toolbar.toolbars.EditorToolBar
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
import indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.SubBarItem

@EditorToolBar
class SettingTool : SubBarItem() {

    override fun call(context: TREEditorContext) {
        //注册设置界面 TODO
    }

    override fun getName(): String {
        return "Setting"
    }
}