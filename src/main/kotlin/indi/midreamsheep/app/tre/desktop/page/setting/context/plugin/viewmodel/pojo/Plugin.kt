package indi.midreamsheep.app.tre.desktop.page.setting.context.plugin.viewmodel.pojo

import androidx.compose.runtime.mutableStateOf

class Plugin {
    var name: String? = null
    var fileName: String? = null
    var version: String? = null
    var description: String? = null
    var author: String? = null
    var url: String? = null
    var isClicked = mutableStateOf(false)
}

fun getPluginJson(plugin: Plugin): String {
    return "{" +
            "\"name\":\"" + plugin.name + "\"" +
            ",\"version\":\"" + plugin.version + "\"" +
            ",\"description\":\"" + plugin.description + "\"" +
            ",\"author\":\"" + plugin.author + "\"" +
            ",\"url\":\"" + plugin.url + "\"" +
            "}"
}


