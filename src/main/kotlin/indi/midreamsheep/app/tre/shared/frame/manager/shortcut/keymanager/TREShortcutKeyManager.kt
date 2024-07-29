package indi.midreamsheep.app.tre.shared.frame.manager.shortcut.keymanager

import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker

/**
 * 用于管理全局按键事件的存储
 * */
class TREShortcutKeyManager {

    private val keys = mutableSetOf<Long>()

    fun add(key: Long) = keys.add(key)

    fun remove(key: Long) = keys.remove(key)

    fun match(checker: TREShortcutKeyChecker) = checker.check(keys)
}