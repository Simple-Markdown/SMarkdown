package indi.midreamsheep.app.tre.shared.frame.engine.context

import indi.midreamsheep.app.tre.shared.frame.core.shortcut.TREShortcutKeyManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREObserverManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREBlock

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
    val treObserverManager: TREObserverManager,
    /**当前上下文的块*/
    var block: TREBlock? = null
){
    init {
        treShortcutEvent.context = this
        blockManager.setContext(this)
    }
}