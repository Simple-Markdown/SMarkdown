package indi.midreamsheep.app.tre.desktop.page.editor.ui

import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindow
import indi.midreamsheep.app.tre.desktop.page.editor.context.viewmodel.EditorStateViewModel
import indi.midreamsheep.app.tre.desktop.page.editor.ui.bottom.bottomBar
import indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render.renderList
import indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render.topbar.topBar
import indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.source.sourceEditor

@Composable
fun editorPage(){
    val context = TRELocalEditorWindow.LocalContext.current
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
        topBar()
        Row (Modifier.weight(1f)){
            //左间距
            editor(
                Modifier.weight(10f),
                listState,
            )
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                adapter = ScrollbarAdapter(listState)
            )
        }
        //下方的工具栏
        bottomBar()
    }
}

@Composable
fun editor(
    modifier: Modifier,
    listState: LazyListState
){
    val context = TRELocalEditorWindow.LocalContext.current
    when (context.editorStateViewModel.editorMode.value) {
        EditorStateViewModel.EditorMode.RENDER -> renderList(modifier, listState)
        EditorStateViewModel.EditorMode.SOURCE -> sourceEditor(context, modifier, listState)
    }
}