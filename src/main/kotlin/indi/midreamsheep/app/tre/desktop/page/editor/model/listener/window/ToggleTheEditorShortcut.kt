package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.window

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorWindowShortcut
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.model.listener.TREEditorWindowShortcutListener
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.file.source.TRESourceManager

@EditorWindowShortcut
class ToggleTheEditorShortcut: TREEditorWindowShortcutListener {

    override fun handle(context: TREEditorWindowContext) {
        when (context.editorStateViewModel.editorMode.value) {
            EditorStateViewModel.EditorMode.RENDER -> {
                context.treFileManager.getStateManager().setCurrentBlock(null)
                context.treFileManager = TRESourceManager(context.treFileManager)
                context.editorStateAction.sourceMode()
            }
            EditorStateViewModel.EditorMode.SOURCE -> {
                context.treFileManager = (context.treFileManager as TRESourceManager).getFileManager()
                context.editorStateAction.renderMode()
            }
        }
    }

    override fun isEnable(context: TREEditorWindowContext) = true

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyStrongChecker> {
        return listOf(
            TREShortcutKeyStrongChecker(
                Key.CtrlLeft.keyCode,
                Key.Slash.keyCode
            )
        )
    }
}