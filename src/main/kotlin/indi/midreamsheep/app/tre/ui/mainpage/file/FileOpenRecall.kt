package indi.midreamsheep.app.tre.ui.mainpage.file

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.tre.api.Recall
import indi.midreamsheep.app.tre.model.editor.manager.core.TRELocalFileManager
import indi.midreamsheep.app.tre.ui.editor.editorPage
import java.io.File

class FileOpenRecall(private val file:File) :Recall<MutableState<Boolean>> {
    @Composable
    override fun recall(value: MutableState<Boolean>) {
        Window(
            onCloseRequest = {
                value.value = false
            }
        ){
            editorPage(TRELocalFileManager(file))
        }
    }

}