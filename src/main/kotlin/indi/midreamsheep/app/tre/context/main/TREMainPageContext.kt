package indi.midreamsheep.app.tre.context.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.setting.settings.stroe.Store
import indi.midreamsheep.app.tre.model.main.file.core.TRELocalFile
import indi.midreamsheep.app.tre.model.main.pages.TREMainPageButtonManager
import indi.midreamsheep.app.tre.tool.context.getBean
import indi.midreamsheep.app.tre.ui.main.file.fileChooser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import java.io.File

/**
 * 主页面的上下文
 * */
@Comment
class TREMainPageContext : TREContext {
    /**
     * 左侧点击的按钮的管理器
     * */
    @Injector
    var buttonsManager: TREMainPageButtonManager? = null

    /**
     * 右侧显示的内容
     * */
    var composable :MutableState<@Composable () -> Unit> = mutableStateOf({
        fileChooser(
            Modifier,
            TRELocalFile(File(getBean(Store::class.java).rootPath.getData()))
        )
    })
}