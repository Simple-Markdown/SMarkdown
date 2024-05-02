package indi.midreamsheep.app.tre.shared.render.manager

/**
 * 文件管理器，用于管理文件的读写
 * */
interface TREFileManager {
    fun read():Pair<Boolean,String>
    fun store():Pair<Boolean,String>
    fun isRead():Boolean
    fun getStateManager(): TREBlockManager
    fun getContent():String
    fun setContent(content:String)
}