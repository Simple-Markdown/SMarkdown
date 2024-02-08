package indi.midreamsheep.app.tre.model.editor.line.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELine
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.tre.model.editor.parser.TREOffsetMapping
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.model.styletext.root.TRECoreStyleTextRoot

class CoreTRELine(wrapper: TRELineState) :
    TRELine, TRETextLine {

    val parser = getBean(MarkdownParse::class.java)
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    var styleText:MutableState<TREStyleTextTree> = mutableStateOf(TRECoreStyleTextRoot())
    private var preview:MutableState<Display> = mutableStateOf(CoreDefaultDisplay(this))
    val recall = {
        focus()
    }
    private var wrapper: TRELineState? = wrapper

    private var selection = 0
    private var focusRequester: FocusRequester = FocusRequester()
    var isFocus = mutableStateOf(false)


    override fun focus() {
        isFocus.value = true
        wrapper!!.markdownLineInter.setCurrentMarkdownLineState(wrapper!!)
        content.value = content.value.copy(selection = TextRange(selection))
    }

    override fun releaseFocus() {
        isFocus.value = false
        selection = content.value.selection.start
    }

    override fun getComposable(context: TREEditorContext): @Composable () -> Unit {
        buildContent(wrapper!!.markdownLineInter)
        return {
            if (isFocus.value) {
                editorInput()
            } else {
                preview.value.display()
            }
        }
    }

    override fun getContent(): String {
        return content.value.text
    }

    @Composable
    fun editorInput() {
        BasicTextField(
            value = content.value,
            onValueChange = { newValue ->
                content.value = newValue
            },
            textStyle = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocus.value = it.isFocused
                }
                .padding(top = 3.dp, bottom = 3.dp, start = 0.dp, end = 0.dp),
            visualTransformation = { text ->
                buildContent(wrapper!!.markdownLineInter)
                TransformedText(
                    text = styleText.value.build(),
                    offsetMapping = TREOffsetMapping(styleText.value)
                )
            }
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    override fun focusFromLast() {
        focus()
        val length = content.value.text.length
        content.value = content.value.copy(selection = TextRange(length))
    }

    override fun focusFormStart() {
        focus()
        content.value = content.value.copy(selection = TextRange(0))
    }

    override fun getTextFieldValue(): TextFieldValue {
        return content.value
    }

    override fun setTextFieldValue(value: TextFieldValue) {
        content.value = value
    }

    private fun buildContent(
        context: TREStateManager
    ){
        val (tree,display) = parser.parse(content.value, this, context,recall)
        styleText.value = tree
        preview.value = display
    }
}