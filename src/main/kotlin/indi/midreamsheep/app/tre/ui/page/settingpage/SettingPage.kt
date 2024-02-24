package indi.midreamsheep.app.tre.ui.page.settingpage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.setting.SettingPageContext
import indi.midreamsheep.app.tre.service.language.TRELanguageResource

@Composable
fun settingPage(
    returnMainPage:()->Unit
) {
    val settingPageContext = SettingPageContext()
    Row(
        Modifier.fillMaxSize()
    ) {
        //sidebar
        settingSideBar(Modifier, settingPageContext,returnMainPage)
        //具体的配置项
        setting(Modifier.weight(1f), settingPageContext)
    }
}

@Composable
fun settingSideBar(
    modifier: Modifier,
    settingPageContext: SettingPageContext,
    returnMainPage: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxHeight()
            .background(Color(0xFFF0F0F1))
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                for (settingGroup in settingPageContext.settingGroupViewModel.getSettingGroupList()) {
                    settingButton(settingGroup.name, BitmapPainter(settingGroup.icon)){
                        settingPageContext.settingGroupAction.setCurrentSettingGroup(settingGroup)
                    }
                }
            }
        }
        settingButton(
            displayName = TRELanguageResource.getLanguage("SettingBack","back"),
            displayIcon = rememberVectorPainter(Icons.Filled.ArrowBack),
            onClick = returnMainPage
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
    context: SettingPageContext
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
                            setting.getDisplay().display()
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
                    TRELanguageResource.getLanguage("SettingSave","apply"),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical =  5.dp, horizontal = 10.dp)
                )
            }
        }
    }
}