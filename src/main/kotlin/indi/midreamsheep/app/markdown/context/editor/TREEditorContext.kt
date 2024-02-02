package indi.midreamsheep.app.markdown.context.editor

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.markdown.context.TREContext
import indi.midreamsheep.app.markdown.model.editor.manager.TREFileManager
import indi.midreamsheep.app.markdown.model.shortcut.editor.TREEditorShortcutKeyManager
import indi.midreamsheep.app.markdown.ui.editor.render.renderList
import indi.midreamsheep.app.markdown.ui.setting.settingPage

class TREEditorContext(val editorFileManager: TREFileManager): TREContext {
    var informationDisplay: MutableState<@Composable ()->Unit> = mutableStateOf({})
    var stateString = mutableStateOf("初始化成功")
    var isSourceMode = mutableStateOf(false)
}