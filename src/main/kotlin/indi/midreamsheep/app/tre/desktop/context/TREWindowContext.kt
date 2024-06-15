package indi.midreamsheep.app.tre.desktop.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorWindowContext
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.shared.api.display.Display

/**
 * 保存上下文信息
 * @author midreamsheep
 * */
abstract class TREWindowContext{
    private lateinit var window: TREWindow
    abstract fun getWindowTitle():String
    abstract fun getDisplay(): Display
    open fun previewKeyEvent(key:KeyEvent):Boolean = false
    open fun close(){
        window.close()
    }
    fun setTREWindow(treWindow: TREWindow){
        window = treWindow
    }
}

val LocalContext = compositionLocalOf<TREEditorWindowContext> { error("context is null") }

@Composable
@Suppress("UNCHECKED_CAST")
fun <T> getWindowContext(context:Class<T>) = LocalContext.current as T