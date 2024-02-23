package indi.midreamsheep.app.tre.ui.editor.editors.render

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.tool.expand.simpleClickable

@Composable
fun renderList(
    context: TREEditorContext,
    modifier: Modifier,
    listState: LazyListState
) {
    val stateManager = context.editorFileManager.getStateManager()
    val lineStateList = stateManager.getMarkdownLineStateList()
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(top = 10.dp)
            .simpleClickable {
                lineStateList[lineStateList.size - 1].line.focus()
            }
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Enter) {
                    // 处理回车键的事件
                    true // 表示这个事件已经被处理
                } else {
                    false // 表示这个事件没有被处理，应该传递给其他处理器
                }
            }
    ) {
        for (markdownLineState in lineStateList) {
            item {
                markdownLineState.line.getComposable(context)()
            }
        }
    }
   LaunchedEffect(key1 = stateManager.getCurrentMarkdownLineState().value) {
       if (stateManager.getCurrentMarkdownLineState().value == null){
           return@LaunchedEffect
       }
       val currentIndex = lineStateList.indexOf(stateManager.getCurrentMarkdownLine()!!)
       // 检查当前行是否在可视范围内
       if (currentIndex !in listState.firstVisibleItemIndex..<listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size) {
           // 如果不在可视范围内，滚动到当前行
           listState.scrollToItem(currentIndex)
       }
    }
}