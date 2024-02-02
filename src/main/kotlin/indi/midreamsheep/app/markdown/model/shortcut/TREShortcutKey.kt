package indi.midreamsheep.app.markdown.model.shortcut

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.markdown.context.TREContext

interface TREShortcutKey{
    fun action(context:TREContext)
    fun getKeys():List<List<Long>>
}