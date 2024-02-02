package indi.midreamsheep.app.markdown.ui.main.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector
import indi.midreamsheep.app.markdown.context.main.TREMainPageContext
import indi.midreamsheep.app.markdown.context.setting.settings.stroe.Store
import indi.midreamsheep.app.markdown.model.main.file.core.TRELocalFile
import indi.midreamsheep.app.markdown.model.main.pages.TREMainPageButton
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.main.file.fileChooser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import java.io.File

@ListInjector(target = "mainPageButtons")
@Comment
class TRELocalFileMainPageButton :TREMainPageButton {
    override fun getComposable(context: TREMainPageContext):@Composable () -> Unit {
        return {
            Icon(
                painter = painterResource("baseline_folder_black_18pt_3x.png"),
                contentDescription = "LocalFile",
                modifier = Modifier.size(35.dp)
                    .clickable {
                        context.composable.value = {
                            fileChooser(
                                Modifier,
                                TRELocalFile(File(getBean(Store::class.java).rootPath.getData()))
                            )
                        }
                    }
            )
        }
    }


}