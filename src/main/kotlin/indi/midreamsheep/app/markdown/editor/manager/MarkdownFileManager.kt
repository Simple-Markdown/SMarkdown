package indi.midreamsheep.app.markdown.editor.manager

interface MarkdownFileManager {
    fun read():Pair<Boolean,String>
    fun store():Pair<Boolean,String>
    fun isRead():Boolean
    fun getStateManager():MarkdownStateManager
}