package indi.midreamsheep.app.tre.model.editor.line.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELine
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine

class CoreTRELine(var wrapper: TRELineState) :
    TRELine,TRETextLine {

    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private var selection = 0
    private var isInit: MutableState<Boolean> = mutableStateOf(false)
    var isFocused: MutableState<Boolean> = mutableStateOf(false)
    private var focusRequester: FocusRequester = FocusRequester()

    override fun focus() {
        isFocused.value = true
        wrapper.markdownLineInter.setCurrentMarkdownLineState(wrapper)
        content.value = content.value.copy(selection = TextRange(selection))
    }

    override fun releaseFocus() {
        isFocused.value = false
        isInit.value = false
        selection = content.value.selection.start
    }

    override fun getComposable(context: TREEditorContext):@Composable () -> Unit {
        return {
            if (this.isFocused.value) {
                editorInput(this, context)
            } else {
                editorPreview(this, context)
            }
        }
    }

    override fun getContent(): String {
        return content.value.text
    }

    @Composable
    fun editorInput(
        markdownLine: CoreTRELine,
        context: TREEditorContext
    ){
        BasicTextField(
            value = markdownLine.content.value,
            onValueChange = { newValue ->
                markdownLine.content.value = newValue
            },
            textStyle = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if(!markdownLine.isInit.value){
                        return@onFocusChanged
                    }
                    if (!it.isFocused){
                        markdownLine.releaseFocus()
                    }
                }
                .onPreviewKeyEvent {
                    return@onPreviewKeyEvent context.shortcutAction.textFieldEvent(it)
                }
                .focusRequester(markdownLine.focusRequester)
                .padding(top =  3.dp, bottom = 3.dp, start = 0.dp, end = 0.dp)
        )
        LaunchedEffect(Unit){
            markdownLine.focusRequester.requestFocus()
            markdownLine.isInit.value= true
        }
    }

    @Composable
    fun editorPreview(
        markdownLineState: CoreTRELine,
        context: TREEditorContext
    ){
        getBean(MarkdownParse::class.java).parse(
            markdownLineState.content.value.text,
            markdownLineState,
            context.editorFileManager.getStateManager()
        ){
            markdownLineState.focus()
        }()
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
}