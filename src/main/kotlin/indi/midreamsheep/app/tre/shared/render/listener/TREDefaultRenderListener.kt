package indi.midreamsheep.app.tre.shared.render.listener

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

class TREDefaultRenderListener: TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext) = false
}