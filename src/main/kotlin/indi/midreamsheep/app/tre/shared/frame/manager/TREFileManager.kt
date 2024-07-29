package indi.midreamsheep.app.tre.shared.frame.manager

import indi.midreamsheep.app.tre.model.mainpage.file.TREFile

/**
 * 文件管理器，用于管理文件的读写
 * */
interface TREFileManager {
    /**
     * 读取文本
     * */
    fun read():Pair<Boolean,String>
    /**
     * 将文本通过io流或其他方式进行保存
     * */
    fun store():Pair<Boolean,String>
    /**
     * 判断文本是否已经读取
     * */
    fun isRead():Boolean
    /**
     * 获取block状态管理器
     * */
    fun getStateManager(): TREBlockManager
    /**
     * 获取文本内容
     * */
    fun getContent():String
    /**
     * 重设文本内容
     * */
    fun setContent(content:String)

    fun getTREFile():TREFile
}