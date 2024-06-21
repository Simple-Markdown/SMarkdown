package indi.midreamsheep.app.tre.shared.frame.engine.listener.editor

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler

class TRECoreEditorShortcutEvent: TREShortcutEvent() {

    val manager = getBean(TREEditorShortcutHandlerManager::class.java)

    override fun keyEvent(): Boolean {
        //处理编辑器级事件
        val handler = getHandler()
        if (handler != null) {
            handler.action(context)
            return true
        }
        val treCoreBlock = context.blockManager.getCurrentBlock()
        if (treCoreBlock == null||treCoreBlock !is TRECoreBlock) {
            return false
        }

        return treCoreBlock.render.value.styleText.styleTextTree.keyEvent(
            context,
            treCoreBlock.content.value.selection.start
        )
    }

    private fun getHandler(): TREEditorShortcutHandler? {
        var targetListener: TREEditorShortcutHandler? = null
        var lastWeight = -1
        for (listener in manager.handlers) {
            for (checker in listener.getKeys()) {
                if (!context.keyManager.match(checker)) {
                    continue
                }
                if (checker.weight==Int.MAX_VALUE){
                    if (!listener.isEnable(context)){
                        break
                    }
                    targetListener = listener
                    lastWeight = Int.MAX_VALUE
                    break
                }
                if (checker.weight>lastWeight){
                    if (!listener.isEnable(context)){
                        break
                    }
                    targetListener = listener
                    lastWeight = checker.weight
                }
            }
        }
        return targetListener
    }

}