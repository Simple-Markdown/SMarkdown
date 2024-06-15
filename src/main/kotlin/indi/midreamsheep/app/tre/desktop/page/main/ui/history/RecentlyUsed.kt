package indi.midreamsheep.app.tre.desktop.page.main.ui.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.desktop.app.TREAppContext
import indi.midreamsheep.app.tre.desktop.page.main.context.recentused.pojo.RecentUsed
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindowContext
import java.io.File

@Composable
fun recentUsed(){
    LazyColumn {
        TREAppContext.context.recentFileViewModel.recentFileList.forEach {
            recentUsed ->
                item {
                    recentUsedItem(recentUsed)
                }
        }
    }
}

@Composable
fun recentUsedItem(
    recentUsed: RecentUsed
){
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                TRELocalEditorWindowContext(TRELocalFile(File(recentUsed.path))).register()
            }
            .padding(bottom =  5.dp,start = 3.dp,end =3.dp)
    ) {
        //图标
        Image(
            painter = androidx.compose.ui.res.painterResource("file.png"),
            contentDescription = "file",
            modifier = Modifier.padding(end = 5.dp).size(35.dp)
        )
        //信息
        Column{
            //标题
            Text(
                text = recentUsed.name,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.height(1.dp))
            //路径
            Text(
                text = recentUsed.path,
                color = androidx.compose.ui.graphics.Color.Gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Spacer(Modifier.weight(1f))
    }
}