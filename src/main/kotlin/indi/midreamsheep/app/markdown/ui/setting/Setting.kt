package indi.midreamsheep.app.markdown.ui.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.setting.ConfigManager
import indi.midreamsheep.app.markdown.tool.context.getBean

@Composable
fun settingPage(){
    val configs = getBean(ConfigManager::class.java).configs
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xfff2f2f2)).padding(10.dp)
    ) {
        for (config in configs) {
            item {
                config.getComposable()()
            }
        }
    }
}

/**
 * 可展开的列表
 *
 * @param title 列表标题
 * @param modifier Modifier
 * @param endText 列表标题的尾部文字，默认为空
 * @param subItemStartPadding 子项距离 start 的 padding 值
 * @param subItem 子项
 * */
@Composable
fun ExpandableItem(
    title: String,
    modifier: Modifier = Modifier,
    subItemStartPadding: Int = 6,
    subItem: @Composable () -> Unit
) {
    var isShowSubItem by remember { mutableStateOf(false) }

    val arrowRotateDegrees: Float by animateFloatAsState(if (isShowSubItem) 90f else 0f)

    Column(modifier = modifier,
            verticalArrangement = Arrangement.Center
        ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isShowSubItem = !isShowSubItem
                }
        ) {
            Text(
                text = title,
                style =  MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Rounded.PlayArrow,
                contentDescription = title,
                modifier = Modifier.rotate(arrowRotateDegrees)
            )
        }

        AnimatedVisibility(visible = isShowSubItem) {
            Column(modifier = Modifier.padding(start = subItemStartPadding.dp)) {
                subItem()
            }
        }
    }
}