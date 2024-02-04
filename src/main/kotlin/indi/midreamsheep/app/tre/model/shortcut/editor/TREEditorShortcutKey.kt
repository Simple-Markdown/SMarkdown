package indi.midreamsheep.app.tre.model.shortcut.editor

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey

interface TREEditorShortcutKey : TREShortcutKey{
    fun action(context: TREEditorContext)
    override fun action(context: TREContext) {
        action(context as TREEditorContext)
    }
}