package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.manager.TREShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.manager.shortcut.TREEditorShortcutEvent

class TableBlockShortcutEvent:TREShortcutEvent {

    private lateinit var context: TREEditorContext
    private var standardShortcutEvent: TREEditorShortcutEvent = TREEditorShortcutEvent()

    override fun initContext(context: TREEditorContext) {
        this.context = context
        standardShortcutEvent.initContext(context)
    }

    override fun keyEvent(): Boolean {
        if (quoteKeyEvent()) return true
        return standardShortcutEvent.keyEvent()
    }

    private fun quoteKeyEvent():Boolean{
        return false
    }
}