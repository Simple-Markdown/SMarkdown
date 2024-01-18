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
import indi.midreamsheep.app.markdown.editor.manager.MarkdownLineState
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun editorInput(
    markdownLineState: MarkdownLineState,
    markdownLineStateManager: MarkdownStateManager
){
    BasicTextField(
        value = markdownLineState.content.value,
        onValueChange = {
            markdownLineState.content.value = it
        },
        textStyle = TextStyle(
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        ),modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if(!markdownLineState.isInit.value){
                    return@onFocusChanged
                }
                if (!it.isFocused){
                    markdownLineState.isFocused.value = false
                    markdownLineState.isInit.value = false
                }
            }
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter){
                    if (keyEvent.type != KeyEventType.KeyDown){
                        return@onPreviewKeyEvent true
                    }
                    val markdownLineStateList = markdownLineStateManager.getMarkdownLineStateList()
                    val index = markdownLineStateList.indexOf(markdownLineState)
                    val newMarkdownLineState = MarkdownLineState()
                    markdownLineStateList.add(index+1,newMarkdownLineState)
                    newMarkdownLineState.isFocused.value = true
                    markdownLineState.isInit.value = false
                    markdownLineState.isFocused.value = false
                    return@onPreviewKeyEvent true
                }
                if (keyEvent.key == Key.DirectionUp&&keyEvent.type == KeyEventType.KeyDown){
                    val markdownLineStateList = markdownLineStateManager.getMarkdownLineStateList()
                    val index = markdownLineStateList.indexOf(markdownLineState)
                    if (index==0){
                        return@onPreviewKeyEvent false
                    }
                    markdownLineStateList[index-1].isFocused.value = true
                    markdownLineState.isInit.value = false
                    markdownLineState.isFocused.value = false
                    return@onPreviewKeyEvent true
                }
                if (keyEvent.key == Key.DirectionDown&&keyEvent.type == KeyEventType.KeyDown){
                    val markdownLineStateList = markdownLineStateManager.getMarkdownLineStateList()
                    val index = markdownLineStateList.indexOf(markdownLineState)
                    if (index==markdownLineStateList.size-1){
                        return@onPreviewKeyEvent false
                    }
                    markdownLineStateList[index+1].isFocused.value = true
                    markdownLineState.isInit.value = false
                    markdownLineState.isFocused.value = false
                    return@onPreviewKeyEvent true
                }
                false
            }
            .focusRequester(markdownLineState.focusRequester)
            .padding(vertical =  3.dp)
    )
    LaunchedEffect(Unit){
        markdownLineState.focusRequester.requestFocus()
        markdownLineState.isInit.value= true
    }
}

@Composable
fun editorPreview(
    markdownLineState: MarkdownLineState,
    markdownLineStateManager: MarkdownStateManager
){
    getBean(MarkdownParse::class.java).parse(
        markdownLineState.content.value,
        markdownLineState,
        markdownLineStateManager
    ){
        markdownLineState.isFocused.value = true
    }()
}