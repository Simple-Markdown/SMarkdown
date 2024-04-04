package indi.midreamsheep.app.tre.context.editor

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.editor.action.BottomBarAction
import indi.midreamsheep.app.tre.context.editor.action.ClipboardAction
import indi.midreamsheep.app.tre.context.editor.action.EditorStateAction
import indi.midreamsheep.app.tre.context.editor.action.FileAction
import indi.midreamsheep.app.tre.context.editor.viewmodel.BottomBarViewModel
import indi.midreamsheep.app.tre.context.editor.viewmodel.DialogViewModel
import indi.midreamsheep.app.tre.context.editor.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.model.editor.manager.TREFileManager
import indi.midreamsheep.app.tre.model.listener.shortcut.textfield.TRETextFieldShortcutKeyManager
import indi.midreamsheep.app.tre.tool.ioc.getBean

/**
 * 编辑器的上下文
 * */
class TREEditorContext(/**编辑文件的管理器*/var editorFileManager: TREFileManager): TREContext {

    /**
     * ViewModel
     * */
    val bottomBarViewModel = BottomBarViewModel(this)
    var dialogVewModel = DialogViewModel(this)
    val editorStateViewModel = EditorStateViewModel(this)

    val treTextFieldShortcutKeyManager = getBean(TRETextFieldShortcutKeyManager::class.java)

    /**
     * action
     * */
    val bottomBarAction = BottomBarAction(this)
    val fileAction = FileAction(this)
    val editorStateAction = EditorStateAction(this)
    val clipboardAction = ClipboardAction(this)
}