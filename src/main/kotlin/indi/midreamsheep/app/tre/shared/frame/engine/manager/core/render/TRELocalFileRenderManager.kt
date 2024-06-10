package indi.midreamsheep.app.tre.shared.frame.engine.manager.core.render

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREFileManager
import indi.midreamsheep.app.tre.shared.frame.engine.manager.core.CoreTREStateManager
import indi.midreamsheep.app.tre.shared.frame.engine.manager.core.ManagerReadParser

class TRELocalFileRenderManager(private var file: TREFile) : TREFileManager {

    private val markdownStateManager = CoreTREStateManager()
    private var isRead = false
    private val parser = getBean(ManagerReadParser::class.java)


    override fun read(): Pair<Boolean, String> {
        val readText = file.readText()
        parser.parse(markdownStateManager,readText)
        isRead = true
        return Pair(true, "")
    }

    override fun store(): Pair<Boolean, String> {
        file.writeText(getContent())
        return Pair(true, "")
    }

    override fun isRead(): Boolean {
        return isRead
    }

    override fun getStateManager(): TREBlockManager {
        return markdownStateManager
    }

    override fun getContent(): String {
        var result = ""
        val list = markdownStateManager.getTREBlockList()
        for ((index, treLineState) in list.withIndex()) {
            result += treLineState.getContent()
            if (index != list.size-1 ) {
                result += "\n"
            }
        }
        return result
    }

    override fun setContent(content: String) {
        markdownStateManager.getTREBlockList().clear()
        parser.parse(markdownStateManager,content)
    }
}