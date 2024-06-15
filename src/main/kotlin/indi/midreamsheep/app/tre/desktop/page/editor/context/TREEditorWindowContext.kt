package indi.midreamsheep.app.tre.desktop.page.editor.context

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.BottomBarAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.ClipboardAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.EditorStateAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.FileAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.BottomBarViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.DialogViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREFileManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.file.render.TRELocalFileRenderManager
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.inline.TREInlineShortcutKeyManager

/**
 * 编辑器窗口上下文
 * */
class TREEditorWindowContext(): TREWindowContext(){

    lateinit var treFileManager: TREFileManager
    lateinit var treFile: TREFile

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

    constructor(treFile: TREFile) : this() {
        this.treFile = treFile
        val manager = TRELocalFileRenderManager(treFile)
        //构建核心上下文
        TREEditorContext(
            parentContext = null,
            blockManager = manager.getStateManager(),
            listenerManager =
        )

    }

    override fun getWindowTitle() = treFile.name!!

    override fun getDisplay() = Display{
        {

        }
    }

}