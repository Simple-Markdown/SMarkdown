package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.code.editor

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindow
import indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render.code.codeInputTextField
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TREBlockAbstract
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TREBlockState

class TRECodeBlock(lineState: TREBlockState, type:String): TREBlockAbstract(lineState) {

    val content = mutableStateOf(TextFieldValue(""))
    val codeType = mutableStateOf(type.replace("```",""))
    val focus = mutableStateOf(false)
    val focusRequester = FocusRequester()

    override fun focus() {
        focus.value = true
        getLineState().blockManager.setCurrentBlockState(getLineState())
    }

    override fun releaseFocus() {
         focus.value = false
        getLineState().blockManager.setCurrentBlockState(null)
    }

    override fun getDisplay(): Display {
        return Display{
            {
                val context = TRELocalEditorWindow.LocalContext.current
                codeInputTextField(this, context)
            }
        }
    }

    override fun getContent(): String {
        return "```${codeType.value}\n${content.value.text}\n```\n"
    }

}