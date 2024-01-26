package indi.midreamsheep.app.markdown.ui.editor

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.markdown.editor.line.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.line.core.CoreMarkdownLine
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun editorInput(
    markdownLine: CoreMarkdownLine,
    wrapper: MarkdownLineState,
    markdownLineStateManager: MarkdownStateManager
){
    BasicTextField(
        value = markdownLine.content.value,
        onValueChange = {
            markdownLine.content.value = it
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
                    val newMarkdownLineState = MarkdownLineState(markdownLineStateManager)

                    markdownLineStateList.add(index+1,newMarkdownLineState)

                    newMarkdownLineState.line.focus()
                    markdownLine.releaseFocus()
                    return@onPreviewKeyEvent true
                }

                if (keyEvent.key == Key.DirectionUp&&keyEvent.type == KeyEventType.KeyDown){
/*                    val markdownLineStateList = markdownLineStateManager.getMarkdownLineStateList()
                    val index = markdownLineStateList.indexOf(markdownLine)
                    if (index==0){
                        return@onPreviewKeyEvent false
                    }
                    markdownLineStateList[index-1].isFocused.value = true
                    markdownLine.isInit.value = false
                    markdownLine.isFocused.value = false
                    return@onPreviewKeyEvent true
                }
                if (keyEvent.key == Key.DirectionDown&&keyEvent.type == KeyEventType.KeyDown){
                    val markdownLineStateList = markdownLineStateManager.getMarkdownLineStateList()
                    val index = markdownLineStateList.indexOf(markdownLine)
                    if (index==markdownLineStateList.size-1){
                        return@onPreviewKeyEvent false
                    }
                    markdownLineStateList[index+1].isFocused.value = true
                    markdownLine.isInit.value = false
                    markdownLine.isFocused.value = false*/
                    return@onPreviewKeyEvent true
                }
                false
            }
            .focusRequester(markdownLine.focusRequester)
            .padding(vertical =  3.dp)
    )
    LaunchedEffect(Unit){
        markdownLine.focusRequester.requestFocus()
        markdownLine.isInit.value= true
    }
}

@Composable
fun editorPreview(
    markdownLineState: CoreMarkdownLine,
    wrapper: MarkdownLineState?,
    markdownLineStateManager: MarkdownStateManager
){
    getBean(MarkdownParse::class.java).parse(
        markdownLineState.content.value,
        markdownLineState,
        markdownLineStateManager
    ){
        markdownLineState.focus()
    }()
}