package indi.midreamsheep.app.tre.shared.render.manager.core.source

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import indi.midreamsheep.app.tre.shared.render.manager.TREFileManager
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import logger

class TRESourceManager(private val manager: TREFileManager): TREFileManager {

    private val content:MutableState<String> = mutableStateOf(manager.getContent())

    override fun read(): Pair<Boolean, String> {
        logger.error("should not read file in source mode")
        throw RuntimeException("should not read file in source mode")
    }

    override fun store(): Pair<Boolean, String> {
        logger.error("should not store file in source mode")
        throw RuntimeException("should not store file in source mode")
    }

    override fun isRead(): Boolean {
        return true
    }

    override fun getStateManager(): TREBlockManager {
        logger.error("should not get state manager in source mode")
        throw RuntimeException("should not get state manager in source mode")
    }

    override fun getContent(): String {
        return content.value
    }

    override fun setContent(content: String) {
        this.content.value = content
    }

    fun getState() = content

    fun getFileManager(): TREFileManager {
        manager.setContent(content.value)
        return manager
    }
}