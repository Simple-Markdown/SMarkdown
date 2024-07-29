package indi.midreamsheep.app.tre.shared.frame

import indi.midreamsheep.app.tre.shared.frame.manager.shortcut.keymanager.TREShortcutKeyManager
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlock
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.manager.TREObserverManager
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent

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
    var block: TREBlock? = null,
    val metaData: TREEditorContextMetaData
){
    init {
        treShortcutEvent.initContext(this)
        blockManager.setContext(this)
    }
}