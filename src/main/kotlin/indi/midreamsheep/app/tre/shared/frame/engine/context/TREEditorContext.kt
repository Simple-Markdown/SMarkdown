package indi.midreamsheep.app.tre.shared.frame.engine.context

import indi.midreamsheep.app.tre.shared.frame.core.shortcut.TREShortcutKeyManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREObserverManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREShortcutEvent

class TREEditorContext(
    /**父上下文，为空则为最高上下文*/
    val parentContext: TREEditorContext?,
    /**块管理器*/
    val blockManager: TREBlockManager,
    /**快捷键当前按键管理器*/
    val keyManager: TREShortcutKeyManager,
    /**监听器管理器*/
    val treShortcutEvent: TREShortcutEvent,
    /**观察者上下文*/
    val treObserverManager: TREObserverManager
){
    init {
        treShortcutEvent.context = this
        blockManager.setContext(this)
    }
}