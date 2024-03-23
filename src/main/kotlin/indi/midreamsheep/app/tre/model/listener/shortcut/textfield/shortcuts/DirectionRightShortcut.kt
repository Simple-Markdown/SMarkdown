package indi.midreamsheep.app.tre.model.listener.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.model.listener.shortcut.textfield.TextFileShortcutTool
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@TextFieldShortcutKey
class DirectionRightShortcut: TREEditorShortcutKeyHandler() {
    @Injector
    private var textFileShortcutTool: TextFileShortcutTool? = null

    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val treTextLine = stateManager.getCurrentBlock()
        val index = stateManager.getTREBlockStateList().indexOf(treTextLine)
        treTextLine!!.line.releaseFocus()
        stateManager.getTREBlockStateList()[index+1].line.focusFormStart()
    }

    override fun isEnable(context: TREContext?): Boolean {
        return textFileShortcutTool!!.check(context!!, false)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.DirectionRight.keyCode
            )
        )
    }
}