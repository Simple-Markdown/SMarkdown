package indi.midreamsheep.app.tre.desktop.page.setting.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.constant.page.SettingPageConstant
import indi.midreamsheep.app.tre.desktop.page.setting.TRESettingWindowContext
import indi.midreamsheep.app.tre.desktop.page.setting.ui.plugin.pluginPage
import indi.midreamsheep.app.tre.service.language.TRELanguageResource

@Composable
fun settingPage() {
    var showExtension by remember { mutableStateOf(false) }
    val settingPageContext = TRESettingWindowContext()
    Row(
        Modifier.fillMaxSize()
    ) {
        //sidebar
        settingSideBar(Modifier, settingPageContext){
            showExtension = it
        }
        //具体的配置项
        Box(
            Modifier.weight(1f)
        ) {
            if (showExtension) {
                pluginPage()
            } else {
                setting(Modifier, settingPageContext)
            }
        }
    }
}

@Composable
fun settingSideBar(
    modifier: Modifier,
    settingPageContext: TRESettingWindowContext,
    showExtension: (Boolean) ->Unit,
) {
    Column(
        modifier = modifier.fillMaxHeight()
            .width(IntrinsicSize.Max)
            .background(Color(0xFFF0F0F1))
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())
        ) {
            for (settingGroup in settingPageContext.settingGroupViewModel.getSettingGroupList()) {
                settingButton(settingGroup.name, BitmapPainter(settingGroup.icon)){
                    showExtension(false)
                    settingPageContext.settingGroupAction.setCurrentSettingGroup(settingGroup)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        settingButton(
            displayName = TRELanguageResource.getLanguage(
                SettingPageConstant.PLUGIN_KEY,SettingPageConstant.PLUGIN_VALUE_DEFAULT
            ),
            displayIcon = painterResource("extension.png"),
            onClick = {
                showExtension(true)
            }
        )
    }
}

@Composable
fun settingButton(
    displayName:String,
    displayIcon:Painter?,
    onClick:()->Unit
){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .widthIn(min = 180.dp)
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (displayIcon != null) {
                Image(
                    displayIcon,
                    contentDescription = displayName,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Spacer(modifier = Modifier.size(20.dp))
            }
            Text(
                text = displayName,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@Composable
fun setting(
    modifier: Modifier,
    context: TRESettingWindowContext
) {
    val rememberLazyListState = rememberLazyListState()
    Column(
        modifier.fillMaxSize()
    ) {
        Row(
            Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.padding(10.dp),
                state = rememberLazyListState
            ) {
                val value = context.settingGroupViewModel.currentSettingGroup.value
                for (setting in value.configs) {
                    item {
                        Text(
                            text = setting.settingName,
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            Modifier.background(Color.White)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xFFF0F0F1))
                                .padding(5.dp)
                        ) {
                            setting.getDisplay().getComposable().invoke()
                        }
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                adapter = ScrollbarAdapter(rememberLazyListState)
            )
        }
        Divider()
        //下放的按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(0.dp))
                    .background(Color.Gray.copy(0.1f))
                    .border(1.dp, Color.Black.copy(0.7f), RoundedCornerShape(0.dp))
                    .clickable {
                        context.settingGroupAction.saveCurrentSetting()
                    }
            ) {
                Text(
                    TRELanguageResource.getLanguage(
                        SettingPageConstant.SAVE_KEY,SettingPageConstant.SAVE_VALUE_DEFAULT
                    ),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical =  5.dp, horizontal = 10.dp)
                )
            }
        }
    }
}