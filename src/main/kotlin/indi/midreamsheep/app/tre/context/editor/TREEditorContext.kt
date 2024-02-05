package indi.midreamsheep.app.tre.context.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.model.editor.manager.TREFileManager

/**
 * 编辑器的上下文
 * */
class TREEditorContext(/**编辑文件的管理器*/val editorFileManager: TREFileManager): TREContext {
    /** 插件根据特定需求实现的弹窗通过informationDisplay实现*/
    var informationDisplay: MutableState<@Composable ()->Unit> = mutableStateOf({})
    /**下方显示的文字信息*/
    var stateString = mutableStateOf("初始化成功")
    /**是否是源文件模式*/
    var isSourceMode = mutableStateOf(false)

    /**
     * state
     * */

    /**
     * action
     * */
}