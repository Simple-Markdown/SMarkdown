package indi.midreamsheep.app.markdown.context.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.markdown.context.TREContext
import indi.midreamsheep.app.markdown.model.editor.manager.TREFileManager

class TREEditorContext(val editorFileManager: TREFileManager): TREContext {
    var informationDisplay: MutableState<@Composable ()->Unit> = mutableStateOf({})
    var stateString = mutableStateOf("初始化成功")
}