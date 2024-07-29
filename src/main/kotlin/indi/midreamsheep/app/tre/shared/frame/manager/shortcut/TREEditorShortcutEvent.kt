package indi.midreamsheep.app.tre.shared.frame.manager.shortcut

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.shortcut.TRELineShortcutHandler
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent

class TREEditorShortcutEvent: TREShortcutEvent {

    var context: TREEditorContext? = null
    private val manager = getBean(TREEditorShortcutHandlerManager::class.java)

    override fun keyEvent(): Boolean {
        val treCoreBlock = context!!.blockManager.getCurrentBlock()
        if (treCoreBlock != null&&treCoreBlock is TRECoreBlock) {
            if(treCoreBlock.render.value.styleText.styleTextTree.keyEvent(context!!, treCoreBlock.content.value.selection.start)){
                return true
            }
        }
        val handler = getHandler()
        if (handler != null) {
            handler.action(context!!)
            return true
        }
        return false
    }

    private fun getHandler(): TRELineShortcutHandler? {
        var targetListener: TRELineShortcutHandler? = null
        var lastWeight = -1
        for (listener in manager.handlers) {
            for (checker in listener.getKeys()) {
                if (!context!!.keyManager.match(checker)) {
                    continue
                }
                if (checker.weight==Int.MAX_VALUE){
                    if (!listener.isEnable(context!!)){
                        break
                    }
                    targetListener = listener
                    lastWeight = Int.MAX_VALUE
                    break
                }
                if (checker.weight>lastWeight){
                    if (!listener.isEnable(context!!)){
                        break
                    }
                    targetListener = listener
                    lastWeight = checker.weight
                }
            }
        }
        return targetListener
    }

    override fun initContext(context: TREEditorContext){this.context = context}
}