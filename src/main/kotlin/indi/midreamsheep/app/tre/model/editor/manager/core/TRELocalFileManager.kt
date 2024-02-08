package indi.midreamsheep.app.tre.model.editor.manager.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.model.editor.manager.TREFileManager
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile

class TRELocalFileManager(private var file: TREFile) : TREFileManager {

    private val markdownStateManager = CoreTREStateManager()
    private var isRead = false
    private val parser = getBean(ManagerReadParser::class.java)
    private val content = mutableStateOf("")


    override fun read(): Pair<Boolean, String> {
        val readText = file.readText()
        parser.parse(markdownStateManager,readText)
        isRead = true
        return Pair(true, "")
    }

    override fun store(): Pair<Boolean, String> {
        file.writeText(getSourceContent())
        return Pair(true, "")
    }

    override fun isRead(): Boolean {
        return isRead
    }

    override fun getStateManager(): indi.midreamsheep.app.tre.model.editor.manager.TREStateManager {
        return markdownStateManager
    }

    override fun getSourceContent(): String {
        var result = ""
        val list = markdownStateManager.getMarkdownLineStateList()
        for ((index, treLineState) in list.withIndex()) {
            result += treLineState.line.getContent()
            if (index != list.size) {
                result += "\n"
            }
        }
        return result
    }

    override fun setContent(content: String) {
        markdownStateManager.getMarkdownLineStateList().clear()
        parser.parse(markdownStateManager,content)
    }

    override fun getContent(): MutableState<String> {
        return content
    }
}