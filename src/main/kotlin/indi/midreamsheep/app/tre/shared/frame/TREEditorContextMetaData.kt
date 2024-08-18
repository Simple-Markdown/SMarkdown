package indi.midreamsheep.app.tre.shared.frame

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp

/**
 * 在所有context里传递的源数据，包括编辑器关于窗口的坐标
 * */
class TREEditorContextMetaData {
    val startPadding = mutableStateOf(0.dp)
    val endPadding = mutableStateOf(0.dp)
    var editorWith = mutableStateOf(0.dp)
}