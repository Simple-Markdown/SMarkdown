package indi.midreamsheep.app.tre.ui.page.editorpage

import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.context.editor.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.ui.page.editorpage.bottom.bottomBar
import indi.midreamsheep.app.tre.ui.page.editorpage.editors.render.renderList
import indi.midreamsheep.app.tre.ui.page.editorpage.editors.render.topbar.topBar
import indi.midreamsheep.app.tre.ui.page.editorpage.editors.source.sourceEditor

@Composable
fun editorPage(
    context: TREEditorContext
){
    val treFileManager = context.editorFileManager
    if (!treFileManager.isRead()){
        val (result, errorMsg) = treFileManager.read()
        if (!result){
            error(errorMsg)
        }
    }
    val listState = rememberLazyListState()

    context.dialogVewModel.displayDialog()
    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        //上方的工具栏
        topBar(context)
        Row (Modifier.weight(1f)){
            //左间距
            Spacer(Modifier.weight(1f))
            editor(
                Modifier.weight(10f),
                listState,
                context
            )
            //右间距
            Spacer(Modifier.weight(1f))
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                adapter = ScrollbarAdapter(listState)
            )
        }
        //下方的工具栏
        bottomBar(context)
    }
}

@Composable
fun editor(
    modifier: Modifier,
    listState: LazyListState,
    context: TREEditorContext
){
    when (context.editorStateViewModel.editorMode.value) {
        EditorStateViewModel.EditorMode.RENDER -> renderList(context, modifier, listState)
        EditorStateViewModel.EditorMode.SOURCE -> sourceEditor(context, modifier, listState)
    }
}