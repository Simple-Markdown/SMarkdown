package indi.midreamsheep.app.tre.ui.mainpage.file

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile

@Composable
fun fileChooser(
    modifier: Modifier,
    rootFile: TREFile
){
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier.padding(10.dp).verticalScroll(scrollState)
    ) {
        fileList(rootFile)
    }
}

@Composable
fun fileItem(file: TREFile) {
    if (!file.isDirectory()) {
        aFile(file)
        return
    }
    fileList(file)
}

@Composable
fun aFile(
    file: TREFile
){
    val isClicked = remember { mutableStateOf(false) }
    if (isClicked.value) {
        file.getRecall().recall(isClicked)
    }
    Text(
        text = file.getName(),
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(start = 26.dp).clickable{
            isClicked.value = true
        }
    )
}

@Composable
fun fileList(file: TREFile){
    var isShowSubItem by remember { mutableStateOf(false) }

    val arrowRotateDegrees: Float by animateFloatAsState(if (isShowSubItem) 90f else 0f)

    Column(modifier = Modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        isShowSubItem = !isShowSubItem
                    }
                )
        ) {
            Icon(
                painter = painterResource("baseline_arrow_forward_ios_black_18pt_3x.png"),
                contentDescription = file.getName(),
                modifier = Modifier.rotate(arrowRotateDegrees).size(18.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource("baseline_folder_black_18pt_3x.png"),
                contentDescription = file.getName(),
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = file.getName(),
                style =  MaterialTheme.typography.h6
            )
        }

        AnimatedVisibility(visible = isShowSubItem) {
            Column(Modifier.padding(start = 10.dp)){
                for (treFile in file.listSubFiles()) {
                    fileItem(treFile)
                }
            }
        }
    }
}
