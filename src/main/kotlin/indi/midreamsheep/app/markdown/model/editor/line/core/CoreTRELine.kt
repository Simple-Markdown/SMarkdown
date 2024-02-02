package indi.midreamsheep.app.markdown.model.editor.line.core

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.markdown.context.editor.TREEditorContext
import indi.midreamsheep.app.markdown.model.editor.line.TRELineInter
import indi.midreamsheep.app.markdown.model.editor.line.TRELineState
import indi.midreamsheep.app.markdown.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean

class CoreTRELine(private var wrapper: TRELineState) :
    TRELineInter {

    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private var isInit: MutableState<Boolean> = mutableStateOf(false)
    var isFocused: MutableState<Boolean> = mutableStateOf(false)
    private var focusRequester: FocusRequester = FocusRequester()

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
        return content.value.text+"\n"
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun editorInput(
        markdownLine: CoreTRELine,
        wrapper: TRELineState,
        context: TREEditorContext
    ){
        val markdownLineStateManager = context.editorFileManager.getStateManager()
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
                .onPreviewKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.Enter){
                        if (keyEvent.type != KeyEventType.KeyDown){
                            return@onPreviewKeyEvent true
                        }
                        val markdownLineStateList = markdownLineStateManager.getMarkdownLineStateList()
                        val index = markdownLineStateList.indexOf(wrapper)
                        val newMarkdownLineState =
                            TRELineState(markdownLineStateManager)

                        markdownLineStateList.add(index+1,newMarkdownLineState)

                        newMarkdownLineState.line.focus()
                        markdownLine.releaseFocus()
                        return@onPreviewKeyEvent true
                    }
                    if (keyEvent.key == Key.DirectionRight&&keyEvent.type == KeyEventType.KeyDown){
                        val cursorAtEnd = markdownLine.content.value.selection.collapsed && markdownLine.content.value.selection.start == markdownLine.content.value.text.length
                        if (cursorAtEnd) {
                            val index = markdownLineStateManager.getMarkdownLineStateList().indexOf(wrapper)
                            if (index < markdownLineStateManager.getMarkdownLineStateList().size - 1) {
                                markdownLine.releaseFocus()
                                markdownLineStateManager.getMarkdownLineStateList()[index + 1].line.focus()
                            }
                        }
                    }
                    if (keyEvent.key == Key.DirectionLeft&&keyEvent.type == KeyEventType.KeyDown){
                        val cursorAtEnd = markdownLine.content.value.selection.collapsed && markdownLine.content.value.selection.start == 0
                        if (cursorAtEnd) {
                            val index = markdownLineStateManager.getMarkdownLineStateList().indexOf(wrapper)
                            if (index > 0) {
                                markdownLine.releaseFocus()
                                markdownLineStateManager.getMarkdownLineStateList()[index-1].line.focus()
                            }
                        }
                    }
                    false
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
}