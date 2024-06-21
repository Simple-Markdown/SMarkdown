package indi.midreamsheep.app.tre.shared.frame.engine.listener.editor

import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.TREEditorShortcutHandler
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREEditorShortcutHandlerManager {
    @ListInjector(target = "EditorShortcut")
    val handlers = mutableListOf<TREEditorShortcutHandler>()
}