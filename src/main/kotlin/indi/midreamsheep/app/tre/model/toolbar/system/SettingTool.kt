package indi.midreamsheep.app.tre.model.toolbar.system

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.context.api.annotation.toolbar.toolbars.EditorToolBar
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.toolbar.SubBarItem
import indi.midreamsheep.app.tre.ui.setting.settingPage

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