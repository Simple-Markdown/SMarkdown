package indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut

import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyChecker
import indi.midreamsheep.app.tre.shared.api.tre.TREClassId
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext

interface TREEditorShortcutHandler:TREClassId {
    fun action(context: TREEditorContext)

    fun isEnable(context: TREEditorContext): Boolean

    fun getKeys(): List<TREShortcutKeyChecker>
}