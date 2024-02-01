package indi.midreamsheep.app.markdown.model.editor.line.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.editor.line.TRELineInter
import indi.midreamsheep.app.markdown.model.editor.line.TRELineState
import indi.midreamsheep.app.markdown.ui.editor.editorInput
import indi.midreamsheep.app.markdown.ui.editor.editorPreview

class CoreTRELine(private var wrapper: indi.midreamsheep.app.markdown.model.editor.line.TRELineState) :
    indi.midreamsheep.app.markdown.model.editor.line.TRELineInter {

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

    override fun getComposable(context: TREEditorContext):@Composable () -> Unit {
        return {
            if (this.isFocused.value) {
                editorInput(this, wrapper, context)
            } else {
                editorPreview(this, context)
            }
        }
    }

    override fun getContent(): String {
        return content.value+"\n"
    }
}