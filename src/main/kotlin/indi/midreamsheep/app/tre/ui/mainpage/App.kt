package indi.midreamsheep.app.tre.ui.mainpage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext
import indi.midreamsheep.app.tre.ui.plugin.pluginPage
import indi.midreamsheep.app.tre.ui.setting.settingPage

@Composable
fun mainPage(){
    val context = TREMainPageContext()
    Row {
        //侧边栏
        sideBar(Modifier,context)
        Divider(Modifier.width(1.dp).fillMaxHeight())
        //选择界面
        rightScreen(Modifier.weight(1f),context)
    }
}

@Composable
fun rightScreen(modifier: Modifier, context: TREMainPageContext) {
    Box(modifier = modifier){
        context.rightPageViewModel.getRightPageViewState().display()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun sideBar(modifier: Modifier, context: TREMainPageContext) {
    Column(
        modifier = modifier.fillMaxHeight().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for (button in context.sideBarButtonViewModel.getButtons()) {
            button.getDisplay(context).display()
            Spacer(modifier = Modifier.height(10.dp))
        }
        // 选择框
        Spacer(modifier = Modifier.weight(1f))
        // 插件界面
        Icon(
            painter = painterResource("baseline_extension_black_18pt_3x.png"),
            contentDescription = "Settings",
            modifier = Modifier.size(35.dp)
                .onClick {
                    context.rightPageAction.setRightPageContent {
                        pluginPage()
                    }
                }
        )
        Spacer(modifier = Modifier.height(10.dp))
        // 设置界面
        Icon(
            Icons.Rounded.Settings,
            contentDescription = "Settings",
            modifier = Modifier.size(35.dp)
                .onClick {
                    context.rightPageAction.setRightPageContent {
                        settingPage()
                    }
                }
        )
    }
}
