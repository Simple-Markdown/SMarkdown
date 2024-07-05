package indi.midreamsheep.app.tre.shared.ui.engine.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContext
import indi.midreamsheep.app.tre.shared.ui.compnent.simpleclick.simpleClickable

@Composable
fun treEditor(
    modifier: Modifier = Modifier,
    state:LazyListState = rememberLazyListState(),
    showPreButton:Boolean = false,
    startPadding:MutableState<Dp> = mutableStateOf(Dp.Unspecified),
    endPadding:MutableState<Dp> = mutableStateOf(Dp.Unspecified),
){
    val editorContext = getEditorContext()
    val stateManager = editorContext.blockManager
    val lineStateList = stateManager.getTREBlockList()
    LazyColumn(
        state = state,
        modifier = modifier.fillMaxSize()
            .simpleClickable { stateManager.focusBlock(stateManager.getSize()-1,-1) }
            .onKeyEvent { keyEvent ->
                return@onKeyEvent keyEvent.key == Enter
            }
    ) {
        for(treBlock in lineStateList){
            item {
                var preButtonOffset by remember { mutableStateOf(0.dp) }
                var lineHeight by remember { mutableStateOf(0.dp) }
                Row {
                    Spacer(Modifier.width(startPadding.value))
                    Box(
                        Modifier.weight(1f).zIndex(1f),
                    ){
                        if(showPreButton){
                            Row(
                                modifier = Modifier
                                    .offset(x = -preButtonOffset)
                                    .height(lineHeight)
                                    .zIndex(1f)
                                    .onGloballyPositioned {
                                        preButtonOffset = it.size.width.dp
                                        if(it.size.width.dp>startPadding.value){
                                            startPadding.value = it.size.width.dp
                                        }
                                    }
                                ,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                treBlock.getTREBlockDisplay().getPreButton().getPreButton().getComposable().invoke()
                            }
                        }
                        Box(
                            Modifier.fillMaxWidth()
                                .onGloballyPositioned {
                                    lineHeight = it.size.height.dp
                                }
                                .pointerHoverIcon(PointerIcon.Text)
                        ){
                            treBlock.getTREBlockDisplay().getDisplay().getComposable().invoke()
                        }
                    }
                    Spacer(Modifier.width(endPadding.value))
                }
            }
        }
    }
    LaunchedEffect(key1 = stateManager.getCurrentBlockState().value) {
        if (stateManager.getCurrentBlockState().value == null){
            return@LaunchedEffect
        }
        val currentIndex = lineStateList.indexOf(stateManager.getCurrentBlock()!!)
        if (currentIndex !in state.firstVisibleItemIndex..<state.firstVisibleItemIndex + state.layoutInfo.visibleItemsInfo.size) {
            if (currentIndex==-1){
                return@LaunchedEffect
            }
            state.animateScrollToItem(currentIndex)
        }
    }
}

@Composable
fun treEditorWithoutScroll(
    modifier: Modifier = Modifier,
){
    val editorContext = getEditorContext()
    val stateManager = editorContext.blockManager
    val lineStateList = stateManager.getTREBlockList()
    Column(
        modifier = modifier.fillMaxSize()
            .onKeyEvent { keyEvent ->
                return@onKeyEvent keyEvent.key == Enter
            }
    ) {
        for(treBlock in lineStateList){
            Box(
                Modifier.fillMaxWidth().pointerHoverIcon(PointerIcon.Text)
            ) {
                treBlock.getTREBlockDisplay().getDisplay().getComposable().invoke()
            }
        }
    }
}