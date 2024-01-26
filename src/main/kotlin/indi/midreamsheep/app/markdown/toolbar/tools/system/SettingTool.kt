package indi.midreamsheep.app.markdown.toolbar.tools.system

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.toolbar.SubFloorBar
import indi.midreamsheep.app.markdown.ui.setting.settingPage
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
@ListInjector(target = "systemTools" )
class SettingTool :SubFloorBar {
    @Composable
    override fun call(manager: MarkdownStateManager) {
        println("new")
        //新建窗口
        Window(onCloseRequest = {}) {
            MaterialTheme{
                settingPage()
            }
        }
    }

    override fun getName(): String {
        return "Setting"
    }
}