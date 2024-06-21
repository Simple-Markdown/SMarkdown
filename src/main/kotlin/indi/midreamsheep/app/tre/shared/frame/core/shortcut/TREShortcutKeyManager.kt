package indi.midreamsheep.app.tre.shared.frame.core.shortcut

class TREShortcutKeyManager {

    private val keys = mutableSetOf<Long>()

    fun add(key: Long) = keys.add(key)

    fun remove(key: Long) = keys.remove(key)

    fun match()=false
}