package indi.midreamsheep.app.tre.model.render.parser.paragraph.code.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELine
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.ui.editor.editors.render.code.codeInputTextField

class TRECodeLine(val wrapper:TRELineState,type:String):TRELine {

    val content = mutableStateOf("")
    val codeType = mutableStateOf(type.replace("```",""))
    val focus = mutableStateOf(false)
    val focusRequester = FocusRequester()

    override fun focus() {
        focus.value = true
        wrapper.markdownLineInter.setCurrentMarkdownLineState(wrapper)
    }

    override fun releaseFocus() {
         focus.value = false
    }

    override fun getComposable(context: TREEditorContext):@Composable () -> Unit {
        return {
            codeInputTextField(this, context)
        }
    }

    override fun getContent(): String {
        return "```${codeType}\n${content.value}\n```\n"
    }
}