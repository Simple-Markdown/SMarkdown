package indi.midreamsheep.app.markdown.model.toolbar.system

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.markdown.context.api.annotation.toolbar.EditorToolBar
import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.toolbar.SubBarItem
import indi.midreamsheep.app.markdown.ui.setting.settingPage
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@EditorToolBar
class SettingTool : SubBarItem() {

    @Composable
    override fun call(context: TREEditorContext) {
        //新建窗口
        Window(onCloseRequest = {
            return@Window
        }) {
            MaterialTheme{
                settingPage()
            }
        }
    }

    override fun getName(): String {
        return "Setting"
    }
}