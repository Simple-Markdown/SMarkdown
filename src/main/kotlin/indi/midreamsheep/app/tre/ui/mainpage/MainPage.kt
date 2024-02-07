package indi.midreamsheep.app.tre.ui.mainpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext
import indi.midreamsheep.app.tre.ui.setting.SettingWindow

@Composable
fun mainPage() {
    val context = TREMainPageContext()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(23.dp)
    ) {
        /*标题*/
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource("book.svg"),
                contentDescription = "book"
            )
            Text(
                text = "TextRenderEngineer 2024",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }
        Row {
            leftScreen(
                modifier = Modifier.weight(1.8f),
                context = context
            )
            Spacer(modifier = Modifier.width(20.dp))
            rightScreen(
                modifier = Modifier.weight(1f),
                context = context
            )
        }
    }
}

@Composable
fun leftScreen(
    modifier: Modifier,
    context: TREMainPageContext
) {
    Column(
        modifier
    ) {
        Text(
            text = "最近打开",
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        //最近使用
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))
        ) {

        }
    }
}

@Composable
fun rightScreen(
    modifier: Modifier,
    context: TREMainPageContext
) {
    Column(
        modifier
    ) {
        Text(
            text = "开始使用",
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        //按钮组
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            for (button in context.mainPageButtonViewModel.getButtons()) {
                item {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFEEEBEB))
                            .clickable {
                                button.onClick(context)
                            }
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            button.icon,
                            contentDescription = button.buttonName,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = button.buttonName,
                                style = MaterialTheme.typography.body1
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = button.description,
                                style = MaterialTheme.typography.body2,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    TREAppContext.context.windowAction.registerWindow(
                        TREWindow(
                            windowDisplay = SettingWindow(),
                            name = "SettingWindow"
                        )
                    )
                },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "setting",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Gray
                )
            }
        }
    }
}