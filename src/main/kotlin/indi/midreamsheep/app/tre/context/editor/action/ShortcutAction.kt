package indi.midreamsheep.app.tre.context.editor.action

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyEntity

class ShortcutAction(context:TREEditorContext): TREAction<TREEditorContext>(context) {
    fun editorEvent(keyEvent: KeyEvent): Boolean{
        return context.shortcutViewModel.editorShortcutKeyManager.keyEvent(keyEvent,context)
    }

    fun textFieldEvent(keyEvent: KeyEvent): Boolean{
        return context.shortcutViewModel.textFieldShortcutKeyManager.keyEvent(keyEvent,context)
    }

    fun textFieldEventClear() {
        context.shortcutViewModel.textFieldShortcutKeyManager.clear()
    }

    fun textFiledCheck(keyEntity: TREShortcutKeyEntity) = context.shortcutViewModel.textFieldShortcutKeyManager.check(keyEntity)

    fun textFieldUpdate(keyEvent: KeyEvent) = context.shortcutViewModel.textFieldShortcutKeyManager.update(keyEvent)
}