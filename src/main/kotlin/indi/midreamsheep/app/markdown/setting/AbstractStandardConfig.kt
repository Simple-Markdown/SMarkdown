package indi.midreamsheep.app.markdown.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.context.di.init.annotation.Init
import indi.midreamsheep.app.markdown.context.setting.tool.SettingUtil
import indi.midreamsheep.app.markdown.ui.setting.ExpandableItem
import java.io.File

abstract class AbstractStandardConfig:AbstractConfig {
    object Constants{
       @JvmField  val rootPath = System.getProperty("user.dir")+ File.separator + "configs" + File.separator
    }

    @Init
    fun init(){
        val file = File(Constants.rootPath+getConfigName()+".json")
        if (!file.exists()){
            return
        }
        val readText = file.readText()
        SettingUtil.setData(this,readText)
    }

    override fun getComposable(): @Composable () -> Unit {
        val list = mutableListOf<@Composable () -> Unit>()
        for (config in SettingUtil.getConfigs(this)) {
            list.add(config.getComposable())
        }
        return {
            ExpandableItem(
                title = getConfigName(),
                subItem = {
                    for (composable in list) {
                        composable()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp).clip(RoundedCornerShape(3.dp)).background(Color.White)
            )
        }
    }

    override fun getConfigName(): String {
        return this.javaClass.simpleName
    }

    override fun write(): Pair<Boolean, String> {
        //写入文件
        val file = File(Constants.rootPath+getConfigName()+".json")
        if (!file.exists()){
            file.createNewFile()
        }
        file.writeText(getJsonString())
        return Pair(true,"")
    }

    private fun getJsonString():String{
        return SettingUtil.getJson(this)
    }
}