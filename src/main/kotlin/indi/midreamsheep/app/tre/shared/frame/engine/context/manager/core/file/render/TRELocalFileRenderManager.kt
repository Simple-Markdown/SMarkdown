package indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.file.render

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREFileManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.ManagerReadParser
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.block.TREStateManagerImpl

class TRELocalFileRenderManager(private var file: TREFile) : TREFileManager {

    private val blockManager = TREStateManagerImpl()
    private var isRead = false
    private val parser = getBean(ManagerReadParser::class.java)


    override fun read(): Pair<Boolean, String> {
        val readText = file.readText()
        parser.parse(blockManager,readText)
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
        return blockManager
    }

    override fun getContent(): String {
        var result = ""
        val list = blockManager.getTREBlockList()
        for ((index, treLineState) in list.withIndex()) {
            result += treLineState.getContent()
            if (index != list.size-1 ) {
                result += "\n"
            }
        }
        return result
    }

    override fun setContent(content: String) {
        blockManager.getTREBlockList().clear()
        parser.parse(blockManager,content)
    }
}