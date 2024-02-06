package indi.midreamsheep.app.tre.model.editor.manager

import androidx.compose.runtime.MutableState

interface TREFileManager {
    fun read():Pair<Boolean,String>
    fun store():Pair<Boolean,String>
    fun isRead():Boolean
    fun getStateManager(): TREStateManager
    fun getSourceContent():String
    fun setContent(content:String)
    fun getContent(): MutableState<String>
}