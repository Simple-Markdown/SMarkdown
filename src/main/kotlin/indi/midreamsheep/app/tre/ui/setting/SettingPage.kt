package indi.midreamsheep.app.tre.ui.setting

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.setting.SettingPageContext

@Composable
fun settingPage() {
    val settingPageContext = SettingPageContext()
    Row(
        Modifier.fillMaxSize()
    ) {
        //sidebar
        settingSideBar(Modifier, settingPageContext)
        //具体的配置项
        setting(Modifier.weight(1f), settingPageContext)
    }
}

@Composable
fun settingSideBar(
    modifier: Modifier,
    settingPageContext: SettingPageContext
) {
    Column(
        modifier = modifier.fillMaxHeight()
            .background(Color(0xFFF0F0F1))
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        for (settingGroup in settingPageContext.settingGroupViewModel.getSettingGroupList()) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .widthIn(min = 180.dp)
                    .clickable {
                        settingPageContext.settingGroupAction.setCurrentSettingGroup(settingGroup)
                    }
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val icon = settingGroup.icon
                    if (icon != null) {
                        Image(
                            icon,
                            contentDescription = settingGroup.name,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                    Text(
                        text = settingGroup.name,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun setting(
    modifier: Modifier,
    context: SettingPageContext
) {
    val rememberLazyListState = rememberLazyListState()
    Row {
        LazyColumn(
            modifier = modifier
                .padding(10.dp),
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
}