package indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render

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
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindow
import indi.midreamsheep.app.tre.shared.ui.compnent.simpleclick.simpleClickable

@Composable
fun renderList(
    modifier: Modifier,
    listState: LazyListState
) {
    val stateManager = TRELocalEditorWindow.LocalContext.current.editorFileManager.getStateManager()
    val lineStateList = stateManager.getTREBlockStateList()

    val leftPadding = remember { mutableStateOf(0.dp) }
    val rightPadding = remember { mutableStateOf(0.dp) }
    val lastWidth = remember { mutableStateOf(0.dp) }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp)
            .simpleClickable {
                lineStateList[lineStateList.size - 1].block.focus()
            }
            .onKeyEvent { keyEvent ->
                return@onKeyEvent keyEvent.key == Enter
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
                    modifier = Modifier.zIndex(1f)
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
                            treBlockState.block.preButton.getPreButton().getComposable().invoke()
                        }
                        Box(
                            Modifier.fillMaxWidth()
                                .onGloballyPositioned {
                                    lineHeight = it.size.height.dp
                                }
                        ){
                            treBlockState.block.getDisplay().getComposable().invoke()
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
       if (currentIndex !in listState.firstVisibleItemIndex..<listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size) {
           if (currentIndex==-1){
                return@LaunchedEffect
           }
           listState.animateScrollToItem(currentIndex)
       }
    }
}