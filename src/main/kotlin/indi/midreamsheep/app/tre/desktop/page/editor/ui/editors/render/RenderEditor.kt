package indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContext
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditor

@Composable
fun renderList(
    modifier: Modifier,
    listState: LazyListState
) {
    val context = getEditorContext()
    val startPadding = context.metaData.startPadding
    val endPadding = context.metaData.endPadding
    val editorWith = context.metaData.editorWith
    Row(
        modifier.onGloballyPositioned {
            startPadding.value = it.size.width.dp/10
            endPadding.value = it.size.width.dp/10
            editorWith.value = it.size.width.dp - startPadding.value - endPadding.value
        }
    ) {
        treEditor(
            modifier = Modifier,
            listState,
            true,
            startPadding = startPadding,
            endPadding = endPadding,
        )
    }
}