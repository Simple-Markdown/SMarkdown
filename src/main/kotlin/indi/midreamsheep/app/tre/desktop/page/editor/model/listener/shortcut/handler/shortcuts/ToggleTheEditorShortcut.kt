package indi.midreamsheep.app.tre.desktop.page.editor.model.listener.shortcut.handler.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorWindowContext
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.model.listener.shortcut.handler.TREEditorShortcutKeyHandler
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.file.source.TRESourceManager

@EditorShortcutKey
class ToggleTheEditorShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorWindowContext) {
        when (context.editorStateViewModel.editorMode.value) {
            EditorStateViewModel.EditorMode.RENDER -> {
                context.editorFileManager.getStateManager().setCurrentBlockState(null)
                context.editorFileManager = TRESourceManager(context.editorFileManager)
                context.editorStateAction.sourceMode()
            }
            EditorStateViewModel.EditorMode.SOURCE -> {
                context.editorFileManager = (context.editorFileManager as TRESourceManager).getFileManager()
                context.editorStateAction.renderMode()
            }
        }
    }

    override fun isEnable(context: TREEditorWindowContext?) = true

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