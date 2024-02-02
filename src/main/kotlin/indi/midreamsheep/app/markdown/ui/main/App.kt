package indi.midreamsheep.app.markdown.ui.main

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
import indi.midreamsheep.app.markdown.context.main.TREMainPageContext
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.setting.settingPage

@Composable
fun mainPage(){
    val context = getBean(TREMainPageContext::class.java)
    Row {
        //侧边栏
        sideBar(Modifier,context)
        Divider(Modifier.width(1.dp).fillMaxHeight())
        //选择界面
        selectPage(Modifier.weight(1f),context)
    }
}

@Composable
fun selectPage(modifier: Modifier, context: TREMainPageContext) {
    Box(modifier = modifier.padding(10.dp)){
        context.composable.value.invoke()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun sideBar(modifier: Modifier, context: TREMainPageContext) {
    Column(
        modifier = modifier.fillMaxHeight().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for (button in context.buttonsManager!!.buttons) {
            button.getComposable(context).invoke()
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

                }
        )
        Spacer(modifier = Modifier.height(10.dp))
        // 设置界面
        Icon(
            Icons.Rounded.Settings,
            contentDescription = "Settings",
            modifier = Modifier.size(35.dp)
                .onClick {
                    context.composable.value = {
                        settingPage()
                    }
                }
        )
    }
}