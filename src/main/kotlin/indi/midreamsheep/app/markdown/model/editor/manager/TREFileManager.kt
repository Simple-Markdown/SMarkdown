package indi.midreamsheep.app.markdown.model.editor.manager

interface TREFileManager {
    fun read():Pair<Boolean,String>
    fun store():Pair<Boolean,String>
    fun isRead():Boolean
    fun getStateManager(): indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager
}