package indi.midreamsheep.app.tre.desktop.page.setting.ui.plugin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.constant.AppPathConstant
import indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.TREPluginContext
import indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.viewmodel.pojo.Plugin
import java.awt.Desktop
import java.io.File
import java.net.URI

@Composable
fun pluginPage() {
    val pluginContext = TREPluginContext()
    //插件界面
    Column {
        pluginList(pluginContext,Modifier.weight(1f))
        //下方按钮
        toolBar(pluginContext,Modifier)
    }
}

@Composable
fun toolBar(pluginContext: TREPluginContext, modifier: Modifier.Companion) {
    Row(
        modifier = modifier.fillMaxWidth().background(Color(0xFFE0E0E0)).padding(end = 10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Button(
            modifier = Modifier,
            onClick = {
                pluginContext.pluginAction.save()
            },
            shape = RoundedCornerShape(0.dp),
            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE0E0E0)
            ),
            border = BorderStroke(0.dp, Color.Gray)
        ){
            Text("apply")
        }
    }
}

@Composable
fun pluginList(pluginContext: TREPluginContext, modifier: Modifier) {
    val plugins = pluginContext.pluginViewModel.getPlugins()
    LazyColumn(
        modifier = modifier
    ) {
        items(plugins.size) { index ->
            pluginItem(plugins[index], Modifier)
        }
    }
}

@Composable
fun pluginItem(plugin: Plugin, modifier: Modifier) {
    Box(
        modifier.fillMaxWidth().padding(10.dp)
    ){
        Row {
            //插件图标
            pluginIcon(plugin)
            //插件信息
            Column {
                Row {
                    Text(plugin.name!!)
                    Text(
                        text = "v" + plugin.version!!,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Gray
                    )
                }
                Row {
                    Text(plugin.author!!)
                    Spacer(modifier = Modifier.width(10.dp))
                    displayLink(plugin.url!!)
                }
                Text(plugin.description!!)
            }
            Spacer(modifier = Modifier.weight(1f))
            //勾选框
            Checkbox(
                checked = plugin.isClicked.value,
                onCheckedChange = { plugin.isClicked.value = it }
            )
        }
    }
}

@Composable
fun displayLink(
    url: String
) {
    val annotatedString = AnnotatedString.Builder("Click here to visit GitHub").apply {
        pushStringAnnotation(tag = "URL", annotation = url)
        addStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, fontSize = 12.sp), start = 0, end = length)
    }.toAnnotatedString()

    ClickableText(text = annotatedString) { offset ->
        annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()?.let { annotation ->
            Desktop.getDesktop().browse(URI(annotation.item))
        }
    }
}

@Composable
fun pluginIcon(plugin: Plugin) {
    //从本地通过绝对路径加载插件图标
    val use =
        File(AppPathConstant.ROOT_PATH+ "/plugins/" + plugin.fileName + "/" + "icon.png").inputStream().buffered()
            .use(::loadImageBitmap)
    Image(
        use,
        contentDescription = "plugin icon",
        modifier = Modifier.size(60.dp)
    )
}
