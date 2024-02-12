package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKey
import indi.midreamsheep.app.tre.model.shortcut.editor.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.model.shortcut.textfield.TextFileShortcutTool
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@TextFieldShortcutKey
class DirectionRightShortcut: TREEditorShortcutKeyHandler() {
    @Injector
    private var textFileShortcutTool: TextFileShortcutTool? = null

    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val treTextLine = stateManager.getCurrentMarkdownLine()
        val index = stateManager.getMarkdownLineStateList().indexOf(treTextLine)
        treTextLine!!.line.releaseFocus()
        stateManager.getMarkdownLineStateList()[index+1].line.focusFormStart()
    }

    override fun isEnable(context: TREContext?): Boolean {
        return textFileShortcutTool!!.check(context!!, false)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKey> {
        return listOf(
            TREShortcutKey(
                Key.DirectionRight.keyCode
            )
        )
    }
}