package indi.midreamsheep.app.tre.shared.frame.engine.context.manager

import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext

abstract class TREListenerManager {
    lateinit var context: TREEditorContext
    fun setContext(editorContext:TREEditorContext){
        context = editorContext
    }
}