package indi.midreamsheep.app.tre.model.editor.manager

interface TREFileManager {
    fun read():Pair<Boolean,String>
    fun store():Pair<Boolean,String>
    fun isRead():Boolean
    fun getStateManager(): TREStateManager
    fun getContent():String
    fun setContent(content:String)
}