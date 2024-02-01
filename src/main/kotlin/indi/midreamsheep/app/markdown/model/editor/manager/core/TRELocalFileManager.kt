package indi.midreamsheep.app.markdown.model.editor.manager.core

import indi.midreamsheep.app.markdown.tool.context.getBean
import java.io.File

class TRELocalFileManager(private var file: File) : indi.midreamsheep.app.markdown.model.editor.manager.TREFileManager {

    private val markdownStateManager = indi.midreamsheep.app.markdown.model.editor.manager.core.CoreTREStateManager()
    private var isRead = false

    private val parser = getBean(indi.midreamsheep.app.markdown.model.editor.manager.core.ManagerReadParser::class.java)

    override fun read(): Pair<Boolean, String> {
        val readText = file.readText()
        parser.parse(markdownStateManager,readText)
        isRead = true
        return Pair(true, "")
    }

    override fun store(): Pair<Boolean, String> {
        var result = ""
        for (markdownLineState in markdownStateManager.getMarkdownLineStateList()) {
            result += markdownLineState.line.getContent()
        }
        file.writeText(result)
        return Pair(true, "")
    }

    override fun isRead(): Boolean {
        return isRead
    }

    override fun getStateManager(): indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager {
        return markdownStateManager
    }
}