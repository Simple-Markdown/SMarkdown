package indi.midreamsheep.app.tre.desktop.page.editor.context

import androidx.compose.ui.awt.ComposeWindow
import indi.midreamsheep.app.tre.shared.api.context.TREContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.BottomBarAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.ClipboardAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.EditorStateAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.FileAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.BottomBarViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.DialogViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.shared.render.manager.TREFileManager
import indi.midreamsheep.app.tre.shared.render.listener.shortcut.inline.TREInlineShortcutKeyManager
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean

/**
 * 编辑器的上下文
 * */
class TREEditorContext(/**编辑文件的管理器*/var editorFileManager: TREFileManager): TREContext {

    lateinit var window: ComposeWindow

    /**
     * ViewModel
     * */
    val bottomBarViewModel = BottomBarViewModel(this)
    var dialogVewModel = DialogViewModel(this)
    val editorStateViewModel = EditorStateViewModel(this)

    val treTextFieldShortcutKeyManager = getBean(TREInlineShortcutKeyManager::class.java)

    /**
     * action
     * */
    val bottomBarAction = BottomBarAction(this)
    val fileAction = FileAction(this)
    val editorStateAction = EditorStateAction(this)
    val clipboardAction = ClipboardAction(this)
}