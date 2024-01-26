package indi.midreamsheep.app.markdown.editor.line.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import indi.midreamsheep.app.markdown.editor.line.MarkdownLineInter
import indi.midreamsheep.app.markdown.editor.line.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.ui.editor.editorInput
import indi.midreamsheep.app.markdown.ui.editor.editorPreview

class CoreMarkdownLine(private var wrapper: MarkdownLineState) :MarkdownLineInter {

    var content: MutableState<String> = mutableStateOf("")
    var focusRequester: FocusRequester = FocusRequester()
    var isFocused: MutableState<Boolean> = mutableStateOf(false)
    var isInit: MutableState<Boolean> = mutableStateOf(false)

    override fun focus() {
        isFocused.value = true
        wrapper.markdownLineInter.setCurrentMarkdownLineState(wrapper)
    }

    override fun releaseFocus() {
        isFocused.value = false
        isInit.value = false
    }

    override fun getComposable(markdownLineStateManager: MarkdownStateManager):@Composable () -> Unit {
        return {
            if (this.isFocused.value) {
                editorInput(this, wrapper, markdownLineStateManager)
            } else {
                editorPreview(this,wrapper, markdownLineStateManager)
            }
        }
    }

    override fun getMarkdownContent(): String {
        return content.value+"\n"
    }
}