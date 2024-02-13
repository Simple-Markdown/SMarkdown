package indi.midreamsheep.app.tre.model.shortcut.textfield.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.api.annotation.shortcut.TextFieldShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.model.shortcut.textfield.TextFileShortcutTool
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@TextFieldShortcutKey
class DirectionLeftShortcut: TREEditorShortcutKeyHandler() {

    @Injector
    private var textFileShortcutTool: TextFileShortcutTool? = null

    override fun action(context: TREEditorContext?) {
        val stateManager = context!!.editorFileManager.getStateManager()
        val treTextLine = stateManager.getCurrentMarkdownLine()
        val index = stateManager.getMarkdownLineStateList().indexOf(treTextLine)
        treTextLine!!.line.releaseFocus()
        stateManager.getMarkdownLineStateList()[index-1].line.focusFromLast()
    }

    override fun isEnable(context: TREContext?): Boolean {
        return textFileShortcutTool!!.check(context!!, true)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyTotalMatch> {
        return listOf(
            TREShortcutKeyTotalMatch(
                Key.DirectionLeft.keyCode
            )
        )
    }
}