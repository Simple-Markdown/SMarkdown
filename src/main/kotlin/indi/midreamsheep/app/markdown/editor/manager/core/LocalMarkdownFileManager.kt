package indi.midreamsheep.app.markdown.editor.manager.core

import indi.midreamsheep.app.markdown.editor.manager.MarkdownFileManager
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.tool.context.getBean
import java.io.File

class LocalMarkdownFileManager(private var file: File) :MarkdownFileManager {

    private val markdownStateManager = CoreMarkdownStateManager()
    private var isRead = false

    private val parser = getBean(ManagerReadParser::class.java)

    override fun read(): Pair<Boolean, String> {
        val readText = file.readText()
        parser.parse(markdownStateManager,readText)
        isRead = true
        return Pair(true, "")
    }

    override fun store(): Pair<Boolean, String> {
        var result = ""
        for (markdownLineState in markdownStateManager.getMarkdownLineStateList()) {
            result += markdownLineState.line.getMarkdownContent()
        }
        file.writeText(result)
        return Pair(true, "")
    }

    override fun isRead(): Boolean {
        return isRead
    }

    override fun getStateManager(): MarkdownStateManager {
        return markdownStateManager
    }
}