package indi.midreamsheep.app.tre.shared.render.render.listener

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext

class TREDefaultRenderListener: TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext) = false
}