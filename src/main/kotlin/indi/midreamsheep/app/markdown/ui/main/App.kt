package indi.midreamsheep.app.markdown.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.setting.settings.stroe.Store
import indi.midreamsheep.app.markdown.model.main.file.core.TRELocalFile
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.main.file.fileChooser
import java.io.File

@Composable
fun mainPage(){
    Row {
        //侧边栏
        sideBar(Modifier.width(100.dp))
        //选择界面
        selectPage(Modifier.weight(1f))
    }
}

@Composable
fun selectPage(weight: Modifier) {
    fileChooser(
        weight,
        TRELocalFile(File(getBean(Store::class.java).rootPath.getData()))
    )
}

@Composable
fun sideBar(width: Modifier) {
    Column(
        modifier = width
    ){

    }
}
