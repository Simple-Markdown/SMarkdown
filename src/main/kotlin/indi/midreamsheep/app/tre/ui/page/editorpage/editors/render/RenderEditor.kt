package indi.midreamsheep.app.tre.ui.page.editorpage.editors.render

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.tool.expand.simpleClickable

@Composable
fun renderList(
    context: TREEditorContext,
    modifier: Modifier,
    listState: LazyListState
) {
    val stateManager = context.editorFileManager.getStateManager()
    val lineStateList = stateManager.getTREBlockStateList()

    val leftPadding = remember { mutableStateOf(0.dp) }
    val rightPadding = remember { mutableStateOf(0.dp) }
    val lastWidth = remember { mutableStateOf(0.dp) }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp)
            .onKeyEvent { keyEvent ->
                return@onKeyEvent keyEvent.key == Enter
            }
            .simpleClickable {
                lineStateList[lineStateList.size - 1].line.focus()
            }
            .onGloballyPositioned {
                if(lastWidth.value != it.size.width.dp){
                    leftPadding.value = it.size.width.dp/10
                }
                if(rightPadding.value != it.size.width.dp/10){
                    rightPadding.value = it.size.width.dp/10
                }
            }
    ) {
        for(treBlockState in lineStateList){
            item {
                var preButtonOffset by remember { mutableStateOf(0.dp) }
                var lineHeight by remember { mutableStateOf(0.dp) }
                Row(
                    modifier = Modifier
                        .simpleClickable {
                            treBlockState.line.focus()
                        }
                        .zIndex(1f)
                ) {
                    Spacer(modifier = Modifier.width(leftPadding.value))
                    Box(
                        Modifier.weight(1f)
                            .zIndex(1f)
                    ){
                        Row(
                            modifier = Modifier
                                .onGloballyPositioned {
                                    preButtonOffset = it.size.width.dp
                                    if (preButtonOffset > leftPadding.value){
                                        leftPadding.value = preButtonOffset
                                    }
                                }
                                .offset(x = -preButtonOffset)
                                .height(lineHeight)
                                .zIndex(1f)
                            ,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            treBlockState.line.preButton.getPreButton().getComposable().invoke()
                        }
                        Box(
                            Modifier.fillMaxWidth()
                                .onGloballyPositioned {
                                    lineHeight = it.size.height.dp
                                }
                        ){
                            treBlockState.line.getDisplay(context).getComposable().invoke()
                        }
                    }
                    Spacer(modifier = Modifier.width(leftPadding.value))
                }
            }
        }
    }
   LaunchedEffect(key1 = stateManager.getCurrentBlockState().value) {
       if (stateManager.getCurrentBlockState().value == null){
           return@LaunchedEffect
       }
       val currentIndex = lineStateList.indexOf(stateManager.getCurrentBlock()!!)
       // 检查当前行是否在可视范围内
       if (currentIndex !in listState.firstVisibleItemIndex..<listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size) {
           if (currentIndex==-1){
                return@LaunchedEffect
           }
           // 如果不在可视范围内，滚动到当前行
           listState.animateScrollToItem(currentIndex)
       }
    }
}