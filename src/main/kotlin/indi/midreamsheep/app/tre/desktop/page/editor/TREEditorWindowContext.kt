package indi.midreamsheep.app.tre.desktop.page.editor

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.BottomBarAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.EditorStateAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.action.FileAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.BottomBarViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.model.listener.TREEditorWindowListenerManager
import indi.midreamsheep.app.tre.desktop.page.editor.model.listener.TREEditorWindowShortcutListener
import indi.midreamsheep.app.tre.desktop.page.editor.ui.editorPage
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.core.shortcut.TREShortcutKeyManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREFileManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREObserverManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.file.render.TRELocalFileRenderManager
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.listener.editor.TRECoreEditorListenerManager

/**
 * 编辑器窗口上下文
 * */
class TREEditorWindowContext(): TREWindowContext(){

    lateinit var treFileManager: TREFileManager
    lateinit var treEditorContext: TREEditorContext

    private var editorWindowListenerManager = getBean(TREEditorWindowListenerManager::class.java)
    /**
     * ViewModel
     * */
    val bottomBarViewModel = BottomBarViewModel(this)
    val editorStateViewModel = EditorStateViewModel(this)

    /**
     * action
     * */
    val bottomBarAction = BottomBarAction(this)
    val fileAction = FileAction(this)
    val editorStateAction = EditorStateAction(this)



    constructor(treFile: TREFile) : this() {
        treFileManager = TRELocalFileRenderManager(treFile)
        keyManager = TREShortcutKeyManager()
        //构建核心上下文
        treEditorContext = TREEditorContext(
            parentContext = null,
            blockManager = treFileManager.getStateManager(),
            keyManager = keyManager,
            listenerManager = TRECoreEditorListenerManager(),
            treObserverManager = TREEditorWindowObserverManager(),
        )
    }



    override fun getWindowTitle() = treFileManager.getTREFile().name!!

    override fun getDisplay() = Display{
        {
            if (!treFileManager.isRead()){
                val (result, errorMsg) = treFileManager.read()
                if (!result){
                    error(errorMsg)
                }
            }
            MaterialTheme {
                CompositionLocalProvider(
                    getEditorContextComposition() provides treEditorContext,
                    editorWindowContext provides this
                ) {
                    editorPage()
                }
            }
        }
    }

    override fun previewKeyEvent(key: KeyEvent): Boolean {
        var targetListener: TREEditorWindowShortcutListener? = null
        var lastWeight = -1
        for (listener in editorWindowListenerManager.listeners) {
            for (checker in listener.getKeys()) {
                if (!keyManager.match(checker)) {
                    continue
                }
                if (checker.weight==Int.MAX_VALUE){
                    if (!listener.isEnable(this)){
                        break
                    }
                    targetListener = listener
                    lastWeight = Int.MAX_VALUE
                    break
                }
                if (checker.weight>lastWeight){
                    if (!listener.isEnable(this)){
                        break
                    }
                    targetListener = listener
                    lastWeight = checker.weight
                }
            }
        }
        if (targetListener==null) return false
        targetListener.handle(this)
        return true
    }
}

//通过Composition进行上下文传递
val editorWindowContext = compositionLocalOf<TREEditorWindowContext>{ error("no editor window context") }

@Composable
fun getEditorWindowContext() = editorWindowContext.current

class TREEditorWindowObserverManager: TREObserverManager {

}