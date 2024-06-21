package indi.midreamsheep.app.tre.shared.frame.engine.context.manager

import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext

abstract class TREShortcutEvent(

) {
    lateinit var context: TREEditorContext

    abstract fun keyEvent():Boolean
}