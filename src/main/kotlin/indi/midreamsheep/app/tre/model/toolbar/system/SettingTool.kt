package indi.midreamsheep.app.tre.model.toolbar.system

import indi.midreamsheep.app.tre.api.annotation.toolbar.toolbars.EditorToolBar
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.toolbar.SubBarItem

@EditorToolBar
class SettingTool : SubBarItem() {

    override fun call(context: TREEditorContext) {
        //注册设置界面 TODO
    }

    override fun getName(): String {
        return "Setting"
    }
}