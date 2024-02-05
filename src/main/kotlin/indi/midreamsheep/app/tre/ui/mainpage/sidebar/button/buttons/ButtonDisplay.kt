package indi.midreamsheep.app.tre.ui.mainpage.sidebar.button.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext
import indi.midreamsheep.app.tre.context.setting.settings.stroe.Store
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.ui.mainpage.file.fileChooser
import java.io.File

class ButtonDisplay(val context: TREMainPageContext) : Display {
    @Composable
    override fun display() {
        Icon(
            painter = painterResource("baseline_folder_black_18pt_3x.png"),
            contentDescription = "LocalFile",
            modifier = Modifier.size(35.dp)
                .clickable {
                    context.rightPageAction.setRightPageContent {
                        fileChooser(
                            Modifier,
                            TRELocalFile(File(getBean(Store::class.java).rootPath.getData()))
                        )
                    }
                }
        )
    }
}