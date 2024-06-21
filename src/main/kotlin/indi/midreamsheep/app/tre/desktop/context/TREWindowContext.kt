package indi.midreamsheep.app.tre.desktop.context

import androidx.compose.ui.input.key.KeyEvent
import indi.midreamsheep.app.tre.service.window.TREWindow
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.core.shortcut.TREShortcutKeyManager

/**
 * 保存上下文信息
 * @author midreamsheep
 * */
abstract class TREWindowContext{
    private lateinit var window: TREWindow
    lateinit var keyManager: TREShortcutKeyManager
    abstract fun getWindowTitle():String
    abstract fun getDisplay(): Display
    open fun previewKeyEvent(key:KeyEvent) = false
    open fun close(){
        window.close()
    }
    fun setTREWindow(treWindow: TREWindow){
        window = treWindow
    }
}