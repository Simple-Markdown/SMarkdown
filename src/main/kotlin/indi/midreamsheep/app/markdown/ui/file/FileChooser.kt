package indi.midreamsheep.app.markdown.ui.file

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import indi.midreamsheep.app.markdown.context.setting.settings.stroe.Store
import indi.midreamsheep.app.markdown.editor.manager.core.LocalMarkdownFileManager
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.editor.editor
import indi.midreamsheep.app.markdown.ui.setting.ExpandableItem
import java.io.File
import java.lang.RuntimeException

@Composable
fun fileChooser(){
    val storeConfig = getBean(Store::class.java)
    fileChooser(File(storeConfig.rootPath.getData()))
}

@Composable
fun fileChooser(rootFile:File){
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        fileList(rootFile)
    }
}

@Composable
fun fileList(file: File){
    if (!file.isDirectory) {
        Window(onCloseRequest = {}) {
            MaterialTheme {
                editor(LocalMarkdownFileManager(file))
            }
        }
        return
    }
    val list = file.listFiles()
    Column{
        for (file in list!!) {
            fileItem(file)
        }
    }
}

@Composable
fun fileItem(file: File?) {
    ExpandableItem(
        title = file!!.name,
        subItem = {
            fileList(file)
        },
    )
}
