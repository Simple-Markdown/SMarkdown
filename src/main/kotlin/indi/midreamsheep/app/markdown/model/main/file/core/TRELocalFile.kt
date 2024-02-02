package indi.midreamsheep.app.markdown.model.main.file.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import indi.midreamsheep.app.markdown.model.editor.manager.core.TRELocalFileManager
import indi.midreamsheep.app.markdown.model.main.file.TREFile
import indi.midreamsheep.app.markdown.ui.editor.editor
import java.io.File

class TRELocalFile(private val file:File):TREFile {

    override fun listSubFiles(): List<TREFile> {
        val list = mutableListOf<TREFile>()
        for (listFile in file.listFiles()!!) {
            list.add(TRELocalFile(listFile))
        }
        return list
    }

    override fun isDirectory(): Boolean {
        return file.isDirectory
    }

    override fun getName(): String {
        return file.name
    }

    @Composable
    override fun recall(isClicked: MutableState<Boolean>) {
        Window(onCloseRequest = {isClicked.value = false}) {
            editor(TRELocalFileManager(file))
        }
    }
}