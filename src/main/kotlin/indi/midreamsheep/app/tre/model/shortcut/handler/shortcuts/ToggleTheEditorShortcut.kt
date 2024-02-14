package indi.midreamsheep.app.tre.model.shortcut.handler.shortcuts

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.api.annotation.shortcut.EditorShortcutKey
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.context.editor.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.model.editor.manager.core.source.TRESourceManager
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch
import indi.midreamsheep.app.tre.model.shortcut.handler.TREEditorShortcutKeyHandler

@EditorShortcutKey
class ToggleTheEditorShortcut: TREEditorShortcutKeyHandler() {

    override fun action(context: TREEditorContext) {
        when (context.editorStateViewModel.editorMode.value) {
            EditorStateViewModel.EditorMode.RENDER -> {
                context.editorFileManager.getStateManager().setCurrentMarkdownLineState(null)
                context.editorFileManager = TRESourceManager(context.editorFileManager)
                context.editorStateAction.sourceMode()
            }
            EditorStateViewModel.EditorMode.SOURCE -> {
                context.editorFileManager = (context.editorFileManager as TRESourceManager).getFileManager()
                context.editorStateAction.renderMode()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun getKeys(): List<TREShortcutKeyTotalMatch> {
        return listOf(
            TREShortcutKeyTotalMatch(
                Key.CtrlLeft.keyCode,
                Key.Slash.keyCode
            )
        )
    }
}