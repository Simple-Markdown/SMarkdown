package indi.midreamsheep.app.tre.desktop.page.editor.model.listener

import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class TREEditorWindowListenerManager {
    @ListInjector( target = "EditorWindowShortcut")
    var listeners = mutableListOf<TREEditorWindowShortcutListener>()
}