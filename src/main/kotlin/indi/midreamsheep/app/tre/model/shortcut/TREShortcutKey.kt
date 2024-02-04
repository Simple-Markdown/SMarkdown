package indi.midreamsheep.app.tre.model.shortcut

import indi.midreamsheep.app.tre.context.TREContext

interface TREShortcutKey{
    fun action(context:TREContext)
    fun getKeys():List<List<Long>>
}