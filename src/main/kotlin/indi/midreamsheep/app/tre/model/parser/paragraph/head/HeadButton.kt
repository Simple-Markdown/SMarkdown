package indi.midreamsheep.app.tre.model.parser.paragraph.head

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.tool.expand.simpleClickable

@Composable
fun HeadButton(
    level: Int,
    line: TRECoreBlock,
    styleTextTree: StyleTextHeadRoot,
    stateList: TREStateManager,

    ) {
    var expanded by remember { mutableStateOf(false) }
    Row {
        Box(
            modifier = Modifier
                .simpleClickable {
                    expanded = !expanded
                }
        ){
            Text(
                text = "H$level",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .simpleClickable {
                        expanded = true
                    },
                color = Color.Gray
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.padding(0.dp)
                    .background(Color.White)
            ) {
                for(i in 1..6){
                    DropdownMenuItemContent(onClick = {
                        stateList.executeOperator(
                            HeadChangeCommand(
                                level,
                                i,
                                styleTextTree,
                                stateList.getTREBlockStateList().indexOf(line.lineState)
                            )
                        )
                        expanded = false
                    },) {
                        Text(
                            text = "h${i}",
                            modifier = Modifier.padding(5.dp),
                        )
                    }
                }
                DropdownMenuItemContent(onClick = {
                    stateList.executeOperator(
                        HeadChangeCommand(
                            level,
                            0,
                            styleTextTree,
                            stateList.getTREBlockStateList().indexOf(line.lineState)
                        )
                    )
                    expanded = false
                },) {
                    Text(
                        text = "delete",
                        modifier = Modifier.padding(3.dp),
                        color = Color.Red
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
internal fun DropdownMenuItemContent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = rememberRipple(true)
            )
            .fillMaxWidth()
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        ProvideTextStyle(typography.subtitle1) {
            val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled
            CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
                content()
            }
        }
    }
}