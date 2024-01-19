package indi.midreamsheep.app.markdown.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.config.AbstractConfig
import indi.midreamsheep.app.markdown.context.config.ConfigManager
import indi.midreamsheep.app.markdown.tool.context.getBean

@Composable
fun settingPage(){
    val configs = getBean(ConfigManager::class.java).configs
    LazyColumn {
        items(configs.size){
            config(configs[it])
        }
    }
}

@Composable
fun config(
    config: AbstractConfig
){
    val map = config.getMap()
    Column {
        Text(
            text = config.getConfigName(),
            modifier = Modifier.heightIn(20.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        for ((k, v) in map) {
            Row {
                Text(
                    text = k,
                    modifier = Modifier.heightIn(20.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                BasicTextField(
                    value = v,
                    onValueChange = { map[k] = it },
                    modifier = Modifier.heightIn(20.dp)
                )
            }
        }
    }
}