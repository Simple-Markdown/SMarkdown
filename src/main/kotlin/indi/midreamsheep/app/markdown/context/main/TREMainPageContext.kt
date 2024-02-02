package indi.midreamsheep.app.markdown.context.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.markdown.context.TREContext
import indi.midreamsheep.app.markdown.context.setting.settings.stroe.Store
import indi.midreamsheep.app.markdown.model.main.file.core.TRELocalFile
import indi.midreamsheep.app.markdown.model.main.pages.TREMainPageButtonManager
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.main.file.fileChooser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import java.io.File

@Comment
class TREMainPageContext : TREContext {
    @Injector
    var buttonsManager: TREMainPageButtonManager? = null
    var composable :MutableState<@Composable () -> Unit> = mutableStateOf({
        fileChooser(
            Modifier,
            TRELocalFile(File(getBean(Store::class.java).rootPath.getData()))
        )
    })
}