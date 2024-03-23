package indi.midreamsheep.app.tre.model.parser.paragraph.code.editor

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlock
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState
import indi.midreamsheep.app.tre.ui.page.editorpage.editors.render.code.codeInputTextField

class TRECodeBlock(val wrapper:TREBlockState, type:String): TREBlock {

    val content = mutableStateOf(TextFieldValue(""))
    val codeType = mutableStateOf(type.replace("```",""))
    val focus = mutableStateOf(false)
    val focusRequester = FocusRequester()

    override fun focus() {
        focus.value = true
        wrapper.markdownLineInter.setCurrentBlockState(wrapper)
    }

    override fun releaseFocus() {
         focus.value = false
        wrapper.markdownLineInter.setCurrentBlockState(null)
    }

    override fun getComposable(context: TREEditorContext):Display {
        return Display{
            codeInputTextField(this, context)
        }
    }

    override fun getContent(): String {
        return "```${codeType.value}\n${content.value.text}\n```\n"
    }

    override fun getLineState() = wrapper
}