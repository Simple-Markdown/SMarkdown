package indi.midreamsheep.app.markdown.model.shortcut.editor

import indi.midreamsheep.app.markdown.context.TREContext
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.shortcut.TREShortcutKey

interface TREEditorShortcutKey : TREShortcutKey{
    fun action(context: TREEditorContext)
    override fun action(context: TREContext) {
        action(context as TREEditorContext)
    }
}