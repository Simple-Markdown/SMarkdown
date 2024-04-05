package indi.midreamsheep.app.tre.model.render.listener

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.render.style.styletext.TREStyleTextTree

class TREDefaultRenderListener:TRERenderListener() {
    override fun keyEvent(key: KeyEvent, context: TREEditorContext,styleTextTree: TREStyleTextTree) = false
}