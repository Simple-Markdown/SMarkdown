package indi.midreamsheep.app.tre.context.editor

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.action.*
import indi.midreamsheep.app.tre.context.editor.viewmodel.BottomBarViewModel
import indi.midreamsheep.app.tre.context.editor.viewmodel.DialogViewModel
import indi.midreamsheep.app.tre.context.editor.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.context.editor.viewmodel.ShortcutViewModel
import indi.midreamsheep.app.tre.model.editor.manager.TREFileManager

/**
 * 编辑器的上下文
 * */
class TREEditorContext(/**编辑文件的管理器*/var editorFileManager: TREFileManager): TREContext {

    /**
     * ViewModel
     * */
    val bottomBarViewModel = BottomBarViewModel(this)
    var dialogVewModel = DialogViewModel(this)
    val shortcutViewModel = ShortcutViewModel(this)
    val editorStateViewModel = EditorStateViewModel(this)

    /**
     * action
     * */
    val bottomBarAction = BottomBarAction(this)
    val fileAction = FileAction(this)
    val shortcutAction = ShortcutAction(this)
    val editorStateAction = EditorStateAction(this)
    val clipboardAction = ClipboardAction(this)
}