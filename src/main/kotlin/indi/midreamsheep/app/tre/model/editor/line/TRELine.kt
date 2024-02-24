package indi.midreamsheep.app.tre.model.editor.line

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

interface TRELine {
    /**
     * 从上次获取焦点的位置获取焦点
     * */
    fun focus()
    /**
     * 从最后获取焦点
     * */
    fun focusFromLast(){
        focus()
    }
    /**
     * 从开始获取焦点
     * */
    fun focusFormStart(){
        focus()
    }
    /**
     * 释放焦点
     * */
    fun releaseFocus()
    /**
     * 获取当前的composable
     * */
    fun getComposable(context: TREEditorContext):@Composable ()->Unit
    /**
     * 获取当前的内容
     * */
    fun getContent():String
    fun getLineState():TRELineState
}