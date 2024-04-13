package indi.midreamsheep.app.tre.ui.page.editorpage.editors.render

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import indi.midreamsheep.app.tre.api.TREComposable
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
    var leftPadding by remember { mutableStateOf(0.dp) }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp)
            .simpleClickable {
                lineStateList[lineStateList.size - 1].line.focus()
            }
            .onKeyEvent { keyEvent ->
                return@onKeyEvent keyEvent.key == Enter
            }
    ) {
        for ((index, treBlockState) in lineStateList.withIndex()) {
            item {
                Row(
                    modifier = Modifier
                ) {
                    if(treBlockState.line.preButton!= TREComposable.EMPTY){
                        var boxWith by remember { mutableStateOf(0) }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .onGloballyPositioned {
                                    leftPadding = max(leftPadding, it.size.width.dp)
                                    boxWith = it.size.width
                                }
                        ){
                            treBlockState.line.preButton.getComposable().invoke()
                        }
                    }else{
                        Spacer(modifier = Modifier.width(leftPadding))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                println("awdawdwad")
                                treBlockState.line.focus()
                            }
                    ){
                        treBlockState.line.getDisplay(context).getComposable().invoke()
                    }
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
           listState.scrollToItem(currentIndex)
       }
    }
}