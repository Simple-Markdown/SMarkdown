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
import androidx.compose.ui.text.TextRange
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
                    return@onPreviewKeyEvent keyEvent(it,context)
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

    @OptIn(ExperimentalComposeUiApi::class)
    private fun keyEvent(
        keyEvent: KeyEvent,
        context: TREEditorContext
    ): Boolean{
        val stateManager = context.editorFileManager.getStateManager()
        if (keyEvent.key == Key.Enter){
            if (keyEvent.type != KeyEventType.KeyDown){
                return true
            }
            val markdownLineStateList = stateManager.getMarkdownLineStateList()
            val index = markdownLineStateList.indexOf(wrapper)
            val newMarkdownLineState = TRELineState(stateManager)

            markdownLineStateList.add(index+1,newMarkdownLineState)

            //数据更新
            val start = content.value.selection.start
            val newLineText = content.value.text.substring(start)

            (newMarkdownLineState.line as CoreTRELine).content.value = TextFieldValue(newLineText)
            content.value = TextFieldValue(content.value.text.substring(0,start))

            newMarkdownLineState.line.focus()
            releaseFocus()
            return true
        }
        if (keyEvent.key == Key.DirectionRight&&keyEvent.type == KeyEventType.KeyDown){
            val cursorAtEnd = content.value.selection.collapsed && content.value.selection.start == content.value.text.length
            if (cursorAtEnd) {
                val index = stateManager.getMarkdownLineStateList().indexOf(wrapper)
                if (index < stateManager.getMarkdownLineStateList().size - 1) {
                    releaseFocus()
                    stateManager.getMarkdownLineStateList()[index + 1].line.focusFormStart()
                }
            }
        }
        if (keyEvent.key == Key.DirectionLeft&&keyEvent.type == KeyEventType.KeyDown){
            val cursorAtEnd = content.value.selection.collapsed && content.value.selection.start == 0
            if (cursorAtEnd) {
                val index = stateManager.getMarkdownLineStateList().indexOf(wrapper)
                if (index > 0) {
                    releaseFocus()
                    stateManager.getMarkdownLineStateList()[index-1].line.focusFromLast()
                }
            }
        }
        if (keyEvent.key == Key.Backspace&&keyEvent.type == KeyEventType.KeyDown){
            val index = stateManager.getMarkdownLineStateList().indexOf(wrapper)
            if (index==0){
                return false
            }
            if (content.value.text.isEmpty()){
                releaseFocus()
                stateManager.getMarkdownLineStateList()[index-1].line.focusFromLast()
                stateManager.getMarkdownLineStateList().remove(wrapper)
                return true
            }
            if (content.value.selection.start == 0){
                val lastLine = stateManager.getMarkdownLineStateList()[index-1].line as CoreTRELine
                lastLine.focus()
                val lastLineContent = lastLine.content.value.text
                lastLine.content.value = TextFieldValue(
                    text = lastLineContent+content.value.text,
                    selection = TextRange(lastLineContent.length)
                )
                releaseFocus()
                stateManager.getMarkdownLineStateList().remove(wrapper)
                return true
            }
        }
        return false
    }
}